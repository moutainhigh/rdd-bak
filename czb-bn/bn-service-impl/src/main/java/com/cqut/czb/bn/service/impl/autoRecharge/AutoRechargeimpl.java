package com.cqut.czb.bn.service.impl.autoRecharge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.*;
import com.cqut.czb.bn.entity.dto.WCPLoginBack;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.service.autoRecharge.AutoRechargeService;
import com.cqut.czb.bn.service.automaticRechargeService.AutomaticRechargeService;
import com.cqut.czb.bn.service.petrolRecharge.IPetrolRechargeService;
import com.github.pagehelper.PageHelper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AutoRechargeimpl implements AutoRechargeService {

    private static final MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

    private static final String BaseUrl = "http://95504.net";

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    IPetrolRechargeService petrolRechargeService;

    @Autowired
    AutomaticRechargeService automaticRechargeService;

    //保存Cookie
    public OkHttpClient createClient(String userId) {
        return new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(userId, list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(userId);
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                }).connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000, TimeUnit.MILLISECONDS)
                .build();
    }

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    public byte[] getCode(String userId) {

        Request req = new Request.Builder()
                .get()
                .url(BaseUrl + "/UserControl/Image.aspx?code=newcode")
                .build();
        Call call = createClient(userId).newCall(req);
        try {
            Response res = call.execute();
            return readInputStream(res.body().byteStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public LoginResult login(LoginInput loginInput, String userId) {
        MediaType type = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(type, JSONObject.fromObject(loginInput).toString());
        Gson gson = new Gson();
        Request req = new Request.Builder()
                .post(body)
                .url("http://95504.net/LoginHandler.ashx?code=" + loginInput.getCode() + "&id=" + loginInput.getId() + "&pwd=" + loginInput.getPwd() + "&action=" + loginInput.getAction())
                .build();
        Call call = createClient(userId).newCall(req);
        try {
            Response res = call.execute();
//            System.out.println(JSONObject.fromObject(loginInput).toString());
//            System.out.println(res.body().string());
            String resStr = res.body().string();
            System.out.println(resStr);
            LoginResult loginResult = gson.fromJson(resStr, LoginResult.class);
            return loginResult;
        } catch (Exception e) {
            e.printStackTrace();
            LoginResult loginResult = new LoginResult();
            loginResult.setStatus("-1");
            return loginResult;
        }
//        return null;
    }

    @Override
    public ReadCardOutput readCard(ReadCardInput readCardInput, String userId) {
        try {
            Gson gson = new Gson();
            String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", readCardInput, userId);
            ReadCardOutput output = gson.fromJson(res, new TypeToken<ReadCardOutput>(){}.getType());
            if (output.getResult().equals("1")){
                output.setTemplateId(output.getData().get(0).getTemplate().split(",")[0]);
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            ReadCardOutput output = new ReadCardOutput();
            output.setResult("");
            return output;
        }
    }

    @Override
    synchronized public RechargeOutput recharge(RechargeInput rechargeInput, String userId) {
        try {
            Gson gson = new Gson();
            RechargeOutput rechargeOutput;
            // 自动充值
            if (rechargeInput.getRecordIds() != null && ! rechargeInput.getRecordIds().equals("")){
                boolean isSuccess = false; // 是否成功
                String[] arr = rechargeInput.getRecordIds().split(","); //订单数组
                List<String> list = Arrays.asList(arr);
                Integer count = petrolSalesRecordsMapperExtra.selectCountByWaitRecharge(list);
                if (count.equals(arr.length)){
                    String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", rechargeInput, userId);
                    rechargeOutput = gson.fromJson(res, RechargeOutput.class);
                    if ("1".equals(rechargeOutput.getResult())){
                        isSuccess = true;
                    }
                }else{
                    rechargeOutput = new RechargeOutput();
                    rechargeOutput.setResult("0");
                    rechargeOutput.setErrorMsg("充值失败，已有卡被充值");
                }
                updateSalePetrolRecord(list, rechargeInput.getBalance(), isSuccess, rechargeOutput.getErrorMsg());
                return rechargeOutput;
            }else{ // 手动充值
//                String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", rechargeInput, userId);
//                return gson.fromJson(res, RechargeOutput.class);
                rechargeOutput = new RechargeOutput();
                return rechargeOutput;
            }
        } catch (Exception e) {
            e.printStackTrace();
            RechargeOutput rechargeOutput = new RechargeOutput();
            rechargeOutput.setResult("0");
            rechargeOutput.setErrorMsg("充值失败，通讯异常");
            return rechargeOutput;
        }
    }

    private void updateSalePetrolRecord(List<String> list, double balance, boolean isSuccess,String errorMessage){
        for (String recordId : list){
            // 改变油卡销售记录 并 推送
            PetrolRechargeInputDTO item = petrolSalesRecordsMapperExtra.getRechargeUserInfo(recordId);
            petrolRechargeService.recharge(item);
            // 插入自动充值记录
            String message;
            if (isSuccess) {
                message = "充值成功, 主卡余额为" + (balance - item.getPetrolDenomination());
                balance -= item.getPetrolDenomination();
            }else {
                message = "充值失败，" + errorMessage;
            }
            AutoRechargeRecordDTO autoRechargeRecordDTO = new AutoRechargeRecordDTO();
            autoRechargeRecordDTO.setUserAccount(item.getUserAccount());
            autoRechargeRecordDTO.setPetrolNum(item.getPetrolNum());
            autoRechargeRecordDTO.setPrice(item.getCurrentPrice());
            autoRechargeRecordDTO.setRechargeAmount(item.getPetrolDenomination());
            autoRechargeRecordDTO.setOrderTime(item.getCreateAt());
            autoRechargeRecordDTO.setUserName(item.getRecordType().equals("3") ? "大客户用户" : "平台用户");
            autoRechargeRecordDTO.setStatus(isSuccess ? 1 : 0);
            autoRechargeRecordDTO.setMessage(message);
            automaticRechargeService.insertRecord(autoRechargeRecordDTO);
        }
    }

    @Override
    public SearchCardOutput searchCardId(SearchCardInput searchCardInput, String userId) {
        Gson gson = new Gson();
        String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", searchCardInput, userId);
        SearchCardOutput searchCardOutput = gson.fromJson(res, new TypeToken<SearchCardOutput>(){}.getType());
        return searchCardOutput;
    }



    @Override
    public List<SearchCardUser> searchCardIds(SearchCardInput searchCardInput, String userId) {
        Gson gson = new Gson();
        List<SearchCardUser> returnList = new ArrayList<>();
        String[] ids = searchCardInput.getCardAsns().split(",");
        for(int i = 0; i < ids.length; i++){
            searchCardInput.setCardAsn(ids[i]);
            String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", searchCardInput, userId);
            SearchCardOutput searchCardOutput = gson.fromJson(res, new TypeToken<SearchCardOutput>(){}.getType());
            if (searchCardOutput.getData().size() > 0){
                SearchCardUser temp = searchCardOutput.getData().get(0);
                String cardAsn = temp.getAsn();
                boolean flag = true; //判重处理
                for (SearchCardUser searchCardUser: returnList){
                    if (searchCardUser.getAsn().equals(cardAsn)){
                        flag = false;
                    }
                }
                if (flag)
                    returnList.add(temp);
            }else{
                //不是 该 主卡下 子卡
            }
        }
        return returnList;
    }

    @Override
    public List<PetrolRechargeOutputDTO> getRechargeList(PetrolRechargeInputDTO petrolRechargeInputDTO, String userId) {
        List<PetrolRechargeOutputDTO> returnList = new ArrayList<>();
        List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolRechargeListByAutoRecharge(petrolRechargeInputDTO);
        for (PetrolRechargeOutputDTO rechargeOutputDTO: list){
            String carsAsn = rechargeOutputDTO.getPetrolNum();
            boolean flag = true; //判重处理
            for (PetrolRechargeOutputDTO outputDTO: returnList){
                if (outputDTO.getPetrolNum().equals(carsAsn)){
                    flag = false;
                    break;
                }
            }
            if (flag)
                returnList.add(rechargeOutputDTO);
        }
        return returnList;
    }

    @Override
    public TemplateOutput getTemplateData(TemplateInput templateInput, String userId) {
        Gson gson = new Gson();
        String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", templateInput, userId);
        TemplateOutput templateOutput = gson.fromJson(res, new TypeToken<TemplateOutput>(){}.getType());
        return templateOutput;
    }

    @Override
    public SaveTemplateOutput saveTemplate(SaveTemplateInput saveTemplateInput, String userId) {
        Gson gson = new Gson();
        String res = getWithParamters("/NewBigCustomerTerminal/NewDistributionRead.ashx", saveTemplateInput, userId);
        SaveTemplateOutput saveTemplateOutput = gson.fromJson(res, new TypeToken<SaveTemplateOutput>(){}.getType());
        return saveTemplateOutput;
    }

    private String getWithParamters(String url, Object object, String userId) {
        Map<String, String> stringObjectMap = JSON.parseObject(JSON.toJSONString(object), new TypeReference<Map<String, String>>() {});
        //1 构造Request
        HttpUrl.Builder httpBuider = HttpUrl.parse(BaseUrl + url).newBuilder();
        if (stringObjectMap != null) {
            for (Map.Entry<String, String> param : stringObjectMap.entrySet()) {
                httpBuider.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(httpBuider.build())
                .build();

        //2 将Request提交执行
        try{
            Response response = createClient(userId).newCall(request).execute();
            String res = response.body().string();
            System.out.println(res);
            return res;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**  将流 保存为数据数组
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
