package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatWithdrawMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatBalanceRecord;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatTOWithdrawDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.*;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.personCenterImpl.entity.ResultEntity;
import com.cqut.czb.bn.service.impl.personCenterImpl.entity.TransfersDto;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPWithdrawService;
import com.cqut.czb.bn.util.MD5Utils;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class MyWalletImpl implements MyWallet {
    @Autowired
    private MyWalletMapperExtra myWalletMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    WCPWithdrawService withdrawService;

    @Autowired
    WeChatWithdrawMapperExtra weChatWithdrawMapperExtra;

    @Override
    public BalanceAndInfoIdDTO getBalance(String userId) {
        BalanceAndInfoIdDTO balance = myWalletMapper.getUserAllIncome(userId);

        if (balance == null) {
            InsertIncomeInfo info = new InsertIncomeInfo();
            info.setInfoId(StringUtil.createId());
            info.setUserId(userId);
            int success = myWalletMapper.insertIncomeInfo(info);
            if (success > 0) {
                balance = new BalanceAndInfoIdDTO();
                balance.setBalance(new BigDecimal(0));
                return balance;
            }
        }

        return balance;
    }

    @Override
    public JSONObject getWithdrawLog(String userId) {
        List<WithDrawLogDTO> withDrawLogs = myWalletMapper.getWithdrawLog(userId);
        JSONObject jsonAll = new JSONObject();
        JSONArray jsonAllArray = new JSONArray();

        List<String> yearMonths = new ArrayList<>();

        for (WithDrawLogDTO data : withDrawLogs) {
            data.setCreateTime(data.getCreateTime().replace(".0", ""));
            if (yearMonths.size() == 0) {
                yearMonths.add(data.getYearMonth());
                continue;
            }
            boolean ifExsits = false;
            for (int i = 0; i < yearMonths.size(); i++) {
                if (yearMonths.get(i).equals(data.getYearMonth())) {
                    ifExsits = true;
                    continue;
                }
            }
            if (!ifExsits)
                yearMonths.add(data.getYearMonth());
        }

        yearMonths.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                o1 = o1.replace("-", "");
                o2 = o2.replace("-", "");

                return o2.compareTo(o1);
            }
        });

        for (int i = 0; i < yearMonths.size(); i++) {
            JSONObject json = new JSONObject();
            json.put("yearMonth", yearMonths.get(i));
            JSONArray jsonArray = new JSONArray();

            for (WithDrawLogDTO data : withDrawLogs) {
                if (data.getYearMonth().equals(yearMonths.get(i))) {
                    JSONObject jsonOneLog = new JSONObject();
                    jsonOneLog.put("money", "-" + data.getMoney());
                    System.out.println(data.getCreateTime());
                    jsonOneLog.put("createTime", data.getCreateTime());
                    System.out.println(data.getCreateTime());
                    jsonArray.add(jsonOneLog);
                }
            }
            json.put("info", jsonArray);
            jsonAllArray.add(json);
        }

        jsonAll.put("allInfo", jsonAllArray);

        return jsonAll;
    }

    @Override
    public synchronized JSONResult withDraw(AlipayRecordDTO alipayRecordDTO, String userId) {

        if (!bCryptPasswordEncoder.matches(alipayRecordDTO.getKeyWord(), myWalletMapper.getPsw(userId))) {
            return new JSONResult("账户密码错误", 200, "账户密码错误");
        }

        if (alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal(0)) < 0) {
            return new JSONResult("提现金额不能是负数", 200, "提现金额不能是负数");
        }

        if (alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal("0.1")) < 0) {
            return new JSONResult("提现金额不能低于0.1元", 200, "提现金额不能低于0.1元");
        }

        // 取出余额，进行对比
        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome(userId);

        if (alipayRecordDTO.getPaymentAmount().compareTo(balanceAndInfoId.getBalance()) > 0) {
            return new JSONResult("提现金额超出余额", 200, "提现金额超出余额");
        }

        // 设置提现记录基本信息
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount((alipayRecordDTO.getPaymentAmount().doubleValue()));
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setRemark("支付宝提现");
        incomeLog.setType(1);
        incomeLog.setWithdrawAmount(alipayRecordDTO.getPaymentAmount().doubleValue());
        incomeLog.setWithdrawTo(alipayRecordDTO.getPaymentAccount());
        incomeLog.setWithdrawName(alipayRecordDTO.getPaymentName());

        // 支付宝参数设置
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
                AliPayConfig.merchant_private_key, "json", "utf-8", AliPayConfig.alipay_public_key, "RSA2");
        AlipayFundTransToaccountTransferRequest request1 = new AlipayFundTransToaccountTransferRequest();

        request1.setBizContent("{" + "\"out_biz_no\":\"" + System.currentTimeMillis() + "\","
                + "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + alipayRecordDTO.getPaymentAccount()
                + "\"," + "\"amount\":\"" + alipayRecordDTO.getPaymentAmount() + "\","
                + "\"payer_show_name\":\"重庆爱动体育文化传播有限公司\"," + "\"payee_real_name\":\"" + alipayRecordDTO.getPaymentName()
                + "\"," + "\"remark\":\"" + "提现收入" + "\"," + "}");

        AlipayFundTransToaccountTransferResponse response;
        String string = null;

        try {
            // 进行请求
            response = alipayClient.execute(request1);
            string = response.getBody().toString();

            // 是否成功的后续操作
            if (response.isSuccess()) {
                com.alibaba.fastjson.JSONObject jso = com.alibaba.fastjson.JSONObject.parseObject(response.getBody());
                String orderId = jso.getJSONObject("alipay_fund_trans_toaccount_transfer_response")
                        .getString("order_id");
                // 更新用户已提现金额
                int updateBalance = myWalletMapper.increaseWithdrawed(balanceAndInfoId.getInfoId(), alipayRecordDTO.getPaymentAmount().toString());

                if (updateBalance != 1)
                    return new JSONResult("提现成功，但更新用户余额失败", 500, "提现成功");
                System.out.println(updateBalance);

                // 设置提现记录的sourceId为支付宝返回的单号
                incomeLog.setSourceId(orderId);
                // 插入提现记录
                int insertSuccess = myWalletMapper.insertIncomeLog(incomeLog);
                System.out.println(insertSuccess);
                if (insertSuccess != 1)
                    return new JSONResult("提现成功，但插入提现记录出错", 500, "提现成功");
                return new JSONResult("提现成功", 200, "提现成功");
            } else {
                return new JSONResult("支付宝提现请求失败", 200, "支付宝提现请求失败");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            return new JSONResult("提现过程中出现未知错误", 200, string);
        }

    }


    /**
     * 微信小程序提现到微信
     *
     * @param paymentAmount
     * @param user
     * @return
     */
    @Override
    public synchronized JSONResult wspWithDrawToWeChat(BigDecimal paymentAmount, User user) {
        return new JSONResult("该功能暂未上线", 200, null);
        /**
        if (paymentAmount == null) {
            return new JSONResult("提现金额不能为空", 200, null);
        }
        if (paymentAmount.compareTo(new BigDecimal(0)) < 0) {
            return new JSONResult("提现金额不能是负数", 200, null);
        }

        if (paymentAmount.compareTo(new BigDecimal(10)) < 0) {
            return new JSONResult("提现金额不能低于10元", 200, null);
        }

        // 取出余额，进行对比
        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome(user.getUserId());

        if (paymentAmount.compareTo(balanceAndInfoId.getBalance()) > 0) {
            return new JSONResult("提现金额超出余额", 200, null);
        }

        // 设置提现记录基本信息
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount((paymentAmount.doubleValue()));
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        // 生成提现记录id，并且该id作为提现请求的：商户订单号
        String incomeLogRecordId = StringUtil.createId();
        incomeLog.setRecordId(incomeLogRecordId);
        incomeLog.setRemark("微信小程序提现到微信");
        // 设置类型为提现
        incomeLog.setType(1);
        incomeLog.setWithdrawAmount(paymentAmount.doubleValue());
        // 设置提现到款账户为微信openId
        incomeLog.setWithdrawTo(user.getUserAccount());
        // 使用微信名(昵称)
        incomeLog.setWithdrawName(user.getUserName());

        // 微信参数设置
        String appkey = WCProgramConfig.WECHAT_WITHDRAW_API_KEY;// 微信商户秘钥
        String certPath = "D:\\Project\\Server\\czb-server\\czb-bn\\bn-util\\src\\main\\java\\com\\cqut\\czb\\bn\\util\\certificate\\apiclient_cert.p12";// 微信商户证书路径, 根据实际情况填写

        TransfersDto model = new TransfersDto();// 微信接口请求参数
        model.setMch_appid(WCProgramConfig.app_id); // 申请商户号的appid或商户号绑定的appid
        model.setMchid(WCProgramConfig.mchid); // 商户号
        model.setMch_name("商户A"); // 商户名称
        model.setOpenid(user.getUserAccount()); // 商户appid下，某用户的openid
        model.setAmount(paymentAmount.doubleValue()); // 企业付款金额，这里单位为元
        model.setDesc("囧途宝盒提现");
        model.setPartner_trade_no(incomeLogRecordId); // 商户订单号

        // 微信官方API文档 https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
        ResultEntity iResult = WechatpayUtil.doTransfers(appkey, certPath, model);
        System.out.println(iResult);
        try {
//            // 进行请求
//            response = alipayClient.execute(request1);
//            string = response.getBody().toString();
//
//            // 是否成功的后续操作
//            if (response.isSuccess()) {
//                com.alibaba.fastjson.JSONObject jso = com.alibaba.fastjson.JSONObject.parseObject(response.getBody());
//                String orderId = jso.getJSONObject("alipay_fund_trans_toaccount_transfer_response")
//                        .getString("order_id");
//                // 更新用户已提现金额
//                int updateBalance = myWalletMapper.increaseWithdrawed(balanceAndInfoId.getInfoId(), alipayRecordDTO.getPaymentAmount().toString());
//
//                if (updateBalance != 1)
//                    return new JSONResult("提现成功，但更新用户余额失败", 500, "提现成功");
//                System.out.println(updateBalance);
//
//                // 设置提现记录的sourceId为支付宝返回的单号
//                incomeLog.setSourceId(orderId);
//                // 插入提现记录
//                int insertSuccess = myWalletMapper.insertIncomeLog(incomeLog);
//                System.out.println(insertSuccess);
//                if (insertSuccess != 1)
//                    return new JSONResult("提现成功，但插入提现记录出错", 500, "提现成功");
//                return new JSONResult("提现成功", 200, "提现成功");
//            } else {
//                return new JSONResult("支付宝提现请求失败", 200, "支付宝提现请求失败");
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult("提现过程中出先未知错误", 200, null);
        }
        return null;
         */
    }

    @Override
    public JSONResult wxWithDraw(User user, WeiXinRecordDTO weiXinRecordDTO) {

        System.out.println("打印openId："+weiXinRecordDTO.getOpenId());
        //订单号
        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");

        String nonce_str = WechatParameterConfig.getRandomStr();

        Integer amount = new Double(weiXinRecordDTO.getAmount()*100).intValue();

        String name = weiXinRecordDTO.getWxName();

        String openId = weiXinRecordDTO.getOpenId();

        if (weiXinRecordDTO.getAmount() < 0.1) {
            return new JSONResult("提现金额无法小于0.1元",300);
        }
        WeChatTOWithdrawDTO weChatTOWithdrawDTO = new WeChatTOWithdrawDTO();
        weChatTOWithdrawDTO.setPaymentName(name);
        weChatTOWithdrawDTO.setMoney(BigDecimal.valueOf(weiXinRecordDTO.getAmount()));
        weChatTOWithdrawDTO.setUserId(user.getUserId());
        //查询user信息，完善后进行插入记录
        String infoId = myWalletMapper.getUserInfo(user.getUserId());
        if (infoId != null) {
            weChatTOWithdrawDTO.setInfoId(infoId);
        }else {
            return new JSONResult("没有找到该用户",400);
        }
//        //比较，查询是否超支
        WeChatBalanceRecord weChatBalanceRecord = weChatWithdrawMapperExtra.getBalance(infoId);
        if (BigDecimal.valueOf(weiXinRecordDTO.getAmount()).compareTo(weChatBalanceRecord.getBalance()) > 0) {
            return new JSONResult("提现金额超出余额", 300);
        }

        //密匙
        String certPath = "/usr/local/new8090/cert/apiclient_cert.p12";
        TransfersDto model = new TransfersDto();// 微信接口请求参数
        model.setMch_appid(WCProgramConfig.app_id); // 申请商户号的appid或商户号绑定的appid
        model.setMchid(WCProgramConfig.mchid); // 商户号
        model.setOpenid(openId); // 商户appid下，某用户的openid
        model.setAmount(weiXinRecordDTO.getAmount()); // 企业付款金额，这里单位为元
        model.setDesc("囧途宝盒提现");
        model.setPartner_trade_no(orderId); // 商户订单号

        SortedMap<String, Object> parameters = WechatParameterConfig.getParametersWithDraw(nonce_str,amount,name,orderId,openId);

        String info = WeChatUtils.map2xml(parameters);

        String orderList = "";
        try{
            orderList = HttpRequestHandler.httpsRequest(WeChatH5PayConfig.wallet, info, model, certPath);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(orderList);
//        String orderList = WeChatHttpUtil.httpsRequest(WeChatH5PayConfig.wallet, "POST", info);
        if (orderList.equals("")) {
            return new JSONResult("提现失败",400);
        }

        // 获取xml结果转换为jsonobject
        Map<String, Object> result = new TreeMap<String, Object>();
        result = WeChatUtils.xml2Map(orderList);
        System.out.println(result);

        if (result.get("result_code").equals("FAIL")){
            return new JSONResult("提现失败",400);
        }

        JSONResult insert = withdrawService.toWithdraw(weChatTOWithdrawDTO);
        if (insert.getCode() == 300) {
            return new JSONResult(insert.getMessage(),300);
        }else if (insert.getCode() == 500) {
            return new JSONResult("插入记录或更新金额异常",300);
        }


        return new JSONResult("提现成功",200,result);
    }

    @Override
    public JSONResult wxPostDraw(User user, WeiXinRecordDTO weiXinRecordDTO) {
        System.out.println("生成订单");
        return null;
    }

    /**
     * 获取微信提现接口所需要的签名
     *
     * @param characterEncoding
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String createSign(String characterEncoding, Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=" + WCProgramConfig.WECHAT_WITHDRAW_API_KEY);
        return MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数(xml)
            if (params != null) {
                StringBuilder param = new StringBuilder();
                param.append("<xml>");

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    param.append("<").append(entry.getKey()).append(">");
                    param.append(entry.getValue());
                    param.append("</").append(entry.getKey()).append(">");
                }
                param.append("</xml>");

                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(result);
        return result.toString();
    }
}
