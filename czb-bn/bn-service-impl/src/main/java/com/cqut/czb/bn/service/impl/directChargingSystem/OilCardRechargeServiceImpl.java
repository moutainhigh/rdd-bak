package com.cqut.czb.bn.service.impl.directChargingSystem;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapper;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.*;
import com.cqut.czb.bn.entity.entity.directChargingSystem.UserCardRelation;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PaymentProcess.BusinessProcessService;
import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingOrderService;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import com.cqut.czb.bn.service.fanyong.FanyongLogService;
import com.cqut.czb.bn.service.impl.autoRecharge.SignUtil;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OilCardRechargeServiceImpl implements OilCardRechargeService {

    private String characterEncoding = "UTF-8";

    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Autowired
    private BusinessProcessService refuelingCard;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DictMapperExtra dictMapperExtra;

    @Autowired
    private ElectricityRechargeService electricityRechargeService;

    @Autowired
    private FanYongService fanYongService;

    @Autowired
    private FanyongLogService fanyongLogService;

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    private UserRechargeService userRechargeService;

    @Autowired
    private DirectChargingOrderMapper directChargingOrderMapper;

    @Autowired
    private DirectChargingOrderService directChargingOrderService;

    private TimeTaskManager timeTaskManager = new TimeTaskManager();

    @Override
    public List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type) {
        if (type == 1) {
            return oilCardRechargeMapperExtra.getOrderInfoList(userId, type);
        } else {
            return oilCardRechargeMapperExtra.getOilOrderInfoList(userId, type);
        }
    }

    @Override
    public List<DirectChargingOrderDto> getOnceOrderInfoList(String userId, Integer type) {
        if (type != 1) {
            return oilCardRechargeMapperExtra.getOnceOrderInfoList(userId, type);
        } else {
            if (type == 2) {
                return oilCardRechargeMapperExtra.getOnceCOilOrderInfoList(userId, type);
            } else {
                return oilCardRechargeMapperExtra.getOnceSOilOrderInfoList(userId, type);
            }
        }
    }

    @Override
    public JSONResult getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getAllOrderInfoList(directChargingOrderDto);
        PageInfo<DirectChargingOrderDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getAllOnceOrderInfoList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getAllOnceOrderInfoList(directChargingOrderDto);
        PageInfo<DirectChargingOrderDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getTheStatics(DirectChargingOrderDto directChargingOrderDto) {
        DirectChargingOrderDto withdrawList = oilCardRechargeMapperExtra.getTheStatics(directChargingOrderDto);
        return new JSONResult("列表数据查询成功", 200, withdrawList);
    }

    @Override
    public JSONResult getTotalConsumption(int type) {
        return new JSONResult(oilCardRechargeMapperExtra.getTotalConsumption(type));
    }

    @Override
    public JSONResult isNeedLogin() {
        String isNeedLogin = oilCardRechargeMapperExtra.isNeedLogin();
        return new JSONResult("查询成功", 200, isNeedLogin);
    }

    @Override
    public JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getAllUserCard(directChargingOrderDto);
        PageInfo<DirectChargingOrderDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult updateOrderState(DirectChargingOrderDto directChargingOrderDto) {
        int update = oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto);
        if (update > 0 && directChargingOrderDto.getState() == 2){
            directFanyong(directChargingOrderDto);
        }
        if (update > 0) {
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult bindingOilCard(String userId, OilCardBinging oilCardBinging) {
        if(!oilCardBinging.getPertrolNum().equals(oilCardBinging.getIsPertrolNum()))
            return new JSONResult(500,"输入卡号不一致");

        int oilCardNum = oilCardRechargeMapperExtra.isExistOilCard(oilCardBinging);
        if (oilCardNum > 0)
            return new JSONResult(500,"该卡号已被绑定过,请重新确认卡号");

        // 检查该用户有没有绑过卡号
        int isExist = oilCardRechargeMapperExtra.isExistOilCardUser(userId);
        boolean isSuccess = false;
        if (isExist > 0)
            isSuccess = oilCardRechargeMapperExtra.upDatePetrolNum(userId,oilCardBinging) > 0;
        else {
            UserCardRelation userCardRelation = new UserCardRelation();
            userCardRelation.setRecordId(StringUtil.createId());
            userCardRelation.setUserId(userId);
            if (oilCardBinging.getType() == 0)
                userCardRelation.setPetrolchinaPetrolNum(oilCardBinging.getPertrolNum());
            else if(oilCardBinging.getType() == 1)
                userCardRelation.setSinopecPetrolNum(oilCardBinging.getPertrolNum());
            isSuccess = oilCardRechargeMapperExtra.insertPetrolNum(userCardRelation) > 0;
        }
        return isSuccess ? new JSONResult(200,"绑定成功"):new JSONResult(500,"绑定失败");
    }


    //    直充支付--支付宝
    @Override
    public String AlipayRechargeDirect(DirectChargingOrderDto directChargingOrderDto) {
        //检验是否都为空
        if (directChargingOrderDto == null)
            return "传入数据有误（为空）";
        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("7");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");


        //支付的金额
//        Double money=backMoney( petrol,petrolInputDTO);
        // 获取金额
        Double rechargeAmount = oilCardRechargeMapperExtra.getDirectRechargeAmount(directChargingOrderDto.getCommodityId());

        if (rechargeAmount == null) {
            System.out.println("直冲无此价格");
            return null;
        }
//        if ((oilCardRechargeMapperExtra.getDirectRechargeAmount(directChargingOrderDto.getRealPrice())==null)) {
//            System.out.println("直充无此价格");
//            return null;
//        }
        // 积分校验
        int integralAmount = 0;
        if (directChargingOrderDto.getIntegralAmount() <= oilCardRechargeMapperExtra.getMaxIntegralAmount(directChargingOrderDto.getCommodityId())) {
            integralAmount = directChargingOrderDto.getIntegralAmount();
        } else {
            System.out.println("积分超过本商品最高抵扣额度");
            return null;
        }
        Double amount = rechargeAmount - integralAmount;
        amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        directChargingOrderDto.setRechargeAmount(amount);
        directChargingOrderDto.setRealPrice(rechargeAmount);

        // userId
        String userId = directChargingOrderDto.getUserId();
        //直充类型
        Integer recordType = directChargingOrderDto.getRecordType();
        String userAccount = directChargingOrderDto.getUserAccount();
        String cardholder = directChargingOrderDto.getCardholder();
        String rechargeAccount = directChargingOrderDto.getRechargeAccount();
//        Integer integralAmount = directChargingOrderDto.getIntegralAmount();
        Integer isNew = directChargingOrderDto.getIsNew();
        String cardNum = "";
        System.out.println("起吊参数" + directChargingOrderDto.toString());
        if (recordType == 2){
            cardNum = directChargingOrderDto.getPetrolChinaPetrolNum();
        }else if(recordType == 3){
            cardNum = directChargingOrderDto.getSinopecPetrolNum();
        }
        request.setReturnUrl(AliPayConfig.Return_url);
//        request.setBizModel(AliParameterConfig.getPhonePill(orderId,amount, rechargeAmount, userId, recordType,cardNum,userAccount));//支付订单
        if (recordType == 1){
            request.setBizModel(AliParameterConfig.getPhonePill(orderId,amount, rechargeAmount, recordType,userAccount,cardNum,cardholder,rechargeAccount,integralAmount,userId,isNew));
        }else{
            request.setBizModel(AliParameterConfig.getPetrolPill(orderId,amount, rechargeAmount, recordType,cardNum,userAccount,cardholder,rechargeAccount,integralAmount,userId,isNew));
        }
        request.setNotifyUrl(AliPayConfig.Direct_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        boolean insertSalesRecords = false;
        //插入购买信息
        if (recordType == 1){
            insertSalesRecords= insertPhonePillRecords(directChargingOrderDto,orderId);
        }else{
            directChargingOrderDto.setCardholder(directChargingOrderDto.getUserAccount());
            directChargingOrderDto.setUserAccount(cardNum);
            insertSalesRecords= insertPhonePillRecords(directChargingOrderDto,orderId);
        }
        System.out.println("新增直充充值记录完毕"+insertSalesRecords);
        return orderString;
    }

    //    插入订单
    public boolean insertPhonePillRecords(DirectChargingOrderDto directChargingOrderDto, String orderId) {
        boolean insertRecords = false;
        DirectChargingOrderDto directChargingOrder = new DirectChargingOrderDto();
        directChargingOrder.setUserId(directChargingOrderDto.getUserId());
        directChargingOrder.setOrderId(orderId);
        directChargingOrder.setRechargeAmount(directChargingOrderDto.getRechargeAmount());
        directChargingOrder.setRecordType(directChargingOrderDto.getRecordType());
        directChargingOrder.setPaymentMethod(1);
        directChargingOrder.setRealPrice(directChargingOrderDto.getRealPrice());
        directChargingOrder.setState(0);
        directChargingOrder.setCustomerOrderId(directChargingOrderDto.getCustomerOrderId());
        directChargingOrder.setRechargeAccount(directChargingOrderDto.getRechargeAccount());
        if (directChargingOrderDto.getRecordType() == 1) {
            insertRecords=oilCardRechargeMapperExtra.insertOrder(directChargingOrder)>0;
        } else {
            directChargingOrder.setCardholder(directChargingOrderDto.getCardholder());
            insertRecords=oilCardRechargeMapperExtra.insertOilOrder(directChargingOrder)>0;
        }
        return insertRecords;
    }

    @Override
    public String aliPayReturn(HttpServletRequest request, String consumptionType) {
        // 获取支付宝POST过来反馈信息
        System.out.println(1);
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        params=parseOrder(params,requestParams);
        DirectChargingOrderDto directChargingOrderDto = getParams(params);
        //是否被篡改的标识
        boolean signVerfied = false;
        try {
            signVerfied = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_wap_public_key,
                    AliPayConfig.charset, AliPayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        try {
            if (signVerfied) {
//				支付账单是否一致
                if (isCorrectDataH5(params)) {//交易成功
                    Object[] param = { params };
                    Map result = refuelingCard.AliPayback(param,consumptionType);//7为支付宝支付（用于拓展）
                    if (AlipayConfig.response_success.equals(result.get("success"))) {
                        if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("0")) {
                            System.out.println("尚未开通充值");
                            return AlipayConfig.response_success;
                        }
                        if (directChargingOrderDto.getRecordType()==1){
                            System.out.println("开通充值");
                            phoneRechargeSubmission(directChargingOrderDto);
                            System.out.println("充值参数"+directChargingOrderDto.toString());
                        }else{
                            System.out.println("开通充值");
                            onlineorderSubmission(directChargingOrderDto);
                        }
                        return AlipayConfig.response_success;
                    } else if (AlipayConfig.response_fail.equals(result.get("fail"))) {
                        directChargingOrderDto.setState(3);
                        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
                        return AlipayConfig.response_fail;
                    } else
                        return null;
                } else {//交易失败
                    Object[] param = { params };
                    directChargingOrderDto.setState(3);
                    boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
                    return AlipayConfig.response_fail;
                }
            } else {
                directChargingOrderDto.setState(3);
                boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
                return AlipayConfig.response_fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        directChargingOrderDto.setState(3);
        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
        return AlipayConfig.response_fail;
    }

    @Override
    public String aliElectricityReturn(HttpServletRequest request, String consumptionType) {
        // 获取支付宝POST过来反馈信息
        System.out.println(1);
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        params=parseOrder(params,requestParams);
        DirectChargingOrderDto directChargingOrderDto = getParams(params);

        //是否被篡改的标识
        boolean signVerfied = false;
        try {
            signVerfied = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_wap_public_key,
                    AliPayConfig.charset, AliPayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        try {
            if (signVerfied) {
//				支付账单是否一致
                if (isCorrectDataH5(params)) {//交易成功
                    Object[] param = { params };
                    Map result = refuelingCard.AliPayback(param,consumptionType);//7为支付宝支付（用于拓展）
                    if (AlipayConfig.response_success.equals(result.get("success"))) {
                        if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("0")) {
                            System.out.println("尚未开通充值");
                            return AlipayConfig.response_success;
                        }
                        if (directChargingOrderDto.getRecordType()==1){
                            System.out.println("开通充值");
                            //TODO 第三方充值
//                            phoneRechargeSubmission(directChargingOrderDto);
                            System.out.println("充值参数"+directChargingOrderDto.toString());
                        }else{System.out.println
                                ("开通充值");
                            onlineorderSubmission(directChargingOrderDto);
                        }
                        return AlipayConfig.response_success;
                    } else if (AlipayConfig.response_fail.equals(result.get("fail"))) {
                        directChargingOrderDto.setState(3);
                        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
                        return AlipayConfig.response_fail;
                    } else
                        return null;
                } else {//交易失败
                    Object[] param = { params };
                    directChargingOrderDto.setState(3);
                    boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
                    return AlipayConfig.response_fail;
                }
            } else {
                directChargingOrderDto.setState(3);
                boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
                return AlipayConfig.response_fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        directChargingOrderDto.setState(3);
        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
        return AlipayConfig.response_fail;
    }

    private DirectChargingOrderDto getParams(Map<String, String> param) {
        String[] resDate = param.get("passback_params").split("\\^");
        String[] temp;
        String orgId = "";
        String userAccount = "";
        int recordType = 0;
        String cardNum = "";
        String cardholder = "";
        String rechargeAccount = "";
        double rechargeAmount = 0;

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orgId = temp[1];
            }
            if ("recordType".equals(temp[0])) {
                recordType = Integer.valueOf(temp[1]);
            }
            if ("userAccount".equals(temp[0])) {
                userAccount = temp[1];
            }
            if ("cardNum".equals(temp[0])) {
                cardNum = temp[1];
            }
            if ("rechargeAmount".equals(temp[0])) {
                rechargeAmount = Double.parseDouble(temp[1]);
            }
            if ("cardholder".equals(temp[0])) {
                cardholder = temp[1];
            }
            if ("rechargeAccount".equals(temp[0])) {
                rechargeAccount = temp[1];
            }
        }
        System.out.println(userAccount);
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
        directChargingOrderDto.setOrderId(orgId);
        directChargingOrderDto.setRecordType(recordType);
        directChargingOrderDto.setUserAccount(userAccount);
        directChargingOrderDto.setPetrolChinaPetrolNum(cardNum);
        directChargingOrderDto.setRechargeAmount(rechargeAmount);
        directChargingOrderDto.setCardholder(cardholder);
        directChargingOrderDto.setRechargeAccount(rechargeAccount);
        return directChargingOrderDto;
    }

    @Override
    public void phoneRechargeSubmission(DirectChargingOrderDto directChargingOrderDto){

        String URL = "https://api.36duojing.com/v1/mobile/sloworder";

        //平台编码
        String appKey = "30000503";
        //密匙
        String appSecret = "Rw4lEFfnJqRnjKVuJuLp1rdnJyJ91S1-";
        //订单号
        String orderId = directChargingOrderDto.getOrderId();
        //手机号
        String mobile = directChargingOrderDto.getUserAccount();
        //金额
        Double amount = directChargingOrderDto.getRechargeAmount();
        //第三方接口
        String notifyUrl = AliPayConfig.DirectPhone_url;
        //sign
        TreeMap map = new TreeMap();
        map.put("appKey",appKey);
        map.put("orderId",orderId);
        map.put("mobile",mobile);
        map.put("amount",amount);
        map.put("notifyUrl",notifyUrl);
        String string = "";
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            if (!string.equals("")){
                string+="&";
            }
            string += iterator.next();
        }
        string+="&key=" + appSecret;
        String sign = MD5Util.MD5Encode(string,"UTF-8").toUpperCase();

        String params = "orderId=" + orderId +
                "&mobile=" + mobile +
                "&amount=" + amount +
                "&notifyUrl=" + notifyUrl +
                "&appKey=" + appKey +
                "&sign=" + sign;

                //开始请求
        String sr= HttpRequest.httpRequestPost(URL, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println("话费充值");
        System.out.println(jsonObject);
//        int begin = sr.indexOf("code");
//        int end = sr.indexOf("result");
        String result_code = jsonObject.getString("result_code");
        int return_code = jsonObject.getInt("return_code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (result_code.equals("SUCCESS")) {
            directChargingOrderDto1.setState(5);
        }else {
            directChargingOrderDto1.setState(4);
        }
        System.out.println("话费直冲");
    }

    @Override
    public void onlineorderSubmission(DirectChargingOrderDto directChargingOrderDto) {

    }

    @Override
    public String fastPhoneOrderSubmit(DirectChargingOrderDto directChargingOrderDto){
        System.out.println("话费充值(快充)");

        String key = "5ee4707e4c8540e6891770d97310d881";
        String url = "http://ad.anda888.cn/api/merchant/rec/mobile";

        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", "1ab1c7e09c324394814bef148e1ee46b");
        params.put("cardNo", directChargingOrderDto.getRechargeAccount());
        // 金额以分为单位
        params.put("amount", directChargingOrderDto.getRechargeAmount().intValue()*100);
        params.put("outTradeNo", directChargingOrderDto.getOrderId());
        params.put("requestTime", String.valueOf(System.currentTimeMillis()));
        try {
            params.put("notifyUrl", URLEncoder.encode("http://47.110.9.136:8090/oilCardRecharge/fastCallBack", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        TreeSet<String> sortedSet = new TreeSet<>(params.keySet());
        for (String p : sortedSet) {
            System.out.println(p+":"+params.get(p));
            sb.append(p).append("=").append(params.get(p)).append("&");
        }
        sb.append("key=").append(key);
        System.out.println(sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8");
        params.put("sign", sign);
        params.put("notifyUrl", "http://47.110.9.136:8090/oilCardRecharge/fastCallBack");
        System.out.println("sign:"+sign);


        //开始请求
        String sr = HttpClient4.doPost(url, params);
        System.out.println(directChargingOrderDto.getOrderId());
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 0) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);
        }
        directChargingOrderDto1.setUpName(APIUP.ANDA888_PHONE.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);

        return sr;
    }

    @Override
    public String fastOilOrderSubmit(DirectChargingOrderDto directChargingOrderDto){
        System.out.println("油卡充值(快充)");

        String key = "5ee4707e4c8540e6891770d97310d881";
        String url = "http://ad.anda888.cn/api/merchant/rec/oil";

        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", "1ab1c7e09c324394814bef148e1ee46b");
        params.put("cardNo", directChargingOrderDto.getRechargeAccount());
        // 金额以分为单位
        params.put("amount", directChargingOrderDto.getRechargeAmount().intValue()*100);
        params.put("mobileNo", directChargingOrderDto.getCardholder());
        params.put("outTradeNo", directChargingOrderDto.getOrderId());
        params.put("requestTime", String.valueOf(System.currentTimeMillis()));
        try {
            params.put("notifyUrl", URLEncoder.encode("http://47.110.9.136:8090/oilCardRecharge/fastCallBack", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        TreeSet<String> sortedSet = new TreeSet<>(params.keySet());
        for (String p : sortedSet) {
            System.out.println(p+":"+params.get(p));
            sb.append(p).append("=").append(params.get(p)).append("&");
        }
        sb.append("key=").append(key);
        System.out.println(sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8");
        params.put("sign", sign);
        params.put("notifyUrl", "http://47.110.9.136:8090/oilCardRecharge/fastCallBack");
        System.out.println("sign:"+sign);


        //开始请求
        String sr = HttpClient4.doPost(url, params);
        System.out.println(directChargingOrderDto.getOrderId());
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 0) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);
        }
        directChargingOrderDto1.setUpName(APIUP.ANDA888_OIL.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);

        return sr;
    }


    @Override
    public String fastCallBack(FastBackInfo backInfo) {
        System.out.println("快充油卡话费充值回调");
        System.out.println(backInfo);
        if (backInfo.getOutTradeNo() == null){
            return "SUCCESS";
        }
        DirectChargingOrderDto directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(backInfo.getOutTradeNo());
        if (directChargingOrderDto == null){
            System.out.println("未找到订单");
            return "SUCCESS";
        }

        try {
            if (backInfo.getState().equals("SUCCESS")) {//交易成功
                directChargingOrderDto.setState(2);
                dealOrderExtra(true, directChargingOrderDto);
                System.out.println("充值成功");

            } else if (backInfo.getState().equals("FAIL")){//交易失败
                directChargingOrderDto.setState(4);
                dealOrderExtra(false, directChargingOrderDto);
                System.out.println("充值失败");

            }
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        directChargingOrderDto.setState(4);
        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
        return "SUCCESS";
    }

    private static final Map<Integer, Integer> chenxieAmountMap = new HashMap<>();

    static {
        chenxieAmountMap.put(500, 9100661);
    }
    @Override
    public String chenxieOilRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("chenxie油卡充值");

        String URL="http://47.108.60.212:8088/api/ykOrder";

        String account = directChargingOrderDto.getCardholder();

        Integer amount = directChargingOrderDto.getRechargeAmount().intValue();
        Integer id = null;

        if (chenxieAmountMap.containsKey(amount)){
            id = chenxieAmountMap.get(amount);
        } else {
            throw new Exception("暂不支持该金额");
        }

        String key = "tcx16643742043021";

        String cardNo = directChargingOrderDto.getRechargeAccount();
        if (cardNo == null || cardNo.equals("")){
            throw new Exception("油卡号为空");
        }

        String no = directChargingOrderDto.getOrderId();

        String string = "account=" + account + "&id=" + id + "&key=" + key + "&no=" + no;

        String sign = MD5Util.MD5Encode(string,"UTF-8");

        //设置请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("id", id);
        params.put("key", key);
        params.put("cardNo", cardNo);
        params.put("no", no);
        params.put("sign", sign);

        //开始请求
        String sr = HttpClient4.doPost(URL, params);
        System.out.println(no);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 0) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);

            dealOrderExtra(false, directChargingOrderDto);
        }
        directChargingOrderDto1.setUpName(APIUP.CHENXIE_OIL.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);


        return sr;
    }

    private static final Map<Integer, Integer> chenxiePhoneAmountMap = new HashMap<>();

    static {
        chenxiePhoneAmountMap.put(100, 112711);
        chenxiePhoneAmountMap.put(200, 1127334);
    }

    @Override
    public String chenxiePhoneRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("chenxie话费充值");

        String URL="http://47.108.60.212:8088/api/order";

        String account = directChargingOrderDto.getRechargeAccount();

        Integer amount = directChargingOrderDto.getRechargeAmount().intValue();

        Integer id = null;

        if (chenxiePhoneAmountMap.containsKey(amount)){
            id = chenxiePhoneAmountMap.get(amount);
        } else {
            throw new Exception("暂不支持该金额");
        }

        String key = "tcx16643742043021";

        String no = directChargingOrderDto.getOrderId();

        String string = "account=" + account + "&id=" + id + "&key=" + key + "&no=" + no;

        String sign = MD5Util.MD5Encode(string,"UTF-8");

        //设置请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("id", id);
        params.put("key", key);
        params.put("no", no);
        params.put("sign", sign);

        //开始请求
        String sr = HttpClient4.doPost(URL, params);
        System.out.println(no);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 0) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);

            dealOrderExtra(false, directChargingOrderDto);
        }
        directChargingOrderDto1.setUpName(APIUP.CHENXIE_PHONE.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);


        return sr;
    }

    @Override
    public String oilCardRechargeCallBack(CallBackInfo backInfo) {
        System.out.println("chenxie油卡话费充值回调");
        System.out.println(backInfo);
        if (backInfo.getNo() == null){
            return "ok";
        }
        DirectChargingOrderDto directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(backInfo.getNo());

        if (directChargingOrderDto == null){
            System.out.println("未找到订单");
            return "ok";
        }
        if (directChargingOrderDto.getRecordType() == 8){
            System.out.println("话费");
        }

        try {
            if (backInfo.getStatus() == 2) {//交易成功
                directChargingOrderDto.setState(2);
                System.out.println("充值成功");
                dealOrderExtra(true, directChargingOrderDto);

            } else if (backInfo.getStatus() == 3){//交易失败
                directChargingOrderDto.setState(4);
                dealOrderExtra(false, directChargingOrderDto);
            }
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            directChargingOrderDto.setState(4);
            dealOrderExtra(false, directChargingOrderDto);
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "ok";
        }
    }

    @Override
    public String yfPhoneRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("yf话费充值");

        String URL="http://42.192.125.149/yrapi.php/index/recharge";

        String account = directChargingOrderDto.getRechargeAccount();

        Integer amount = directChargingOrderDto.getRechargeAmount().intValue();

        String operator = directChargingOrderDto.getOperator();

        Integer id = YF_CONSTANT.getID(amount, operator);

        if (id == null){
            throw new Exception("暂不支持该金额或地区");
        }

        String userid = "79";

        String key = "vV7RjDqMm9IKd4sYiguWLSrXo1yb02ex";

        String no = directChargingOrderDto.getOrderId();

        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_num", no);
        params.put("product_id", id);
        params.put("mobile", account);
        params.put("notify_url", "http://47.110.9.136:8090/oilCardRecharge/yfCallBack");
        params.put("userid", userid);
        params.put("amount", amount);
        params.put("price", 210);

        StringBuilder sb = new StringBuilder();
        TreeSet<String> sortedSet = new TreeSet<>(params.keySet());
        for (String p : sortedSet) {
            System.out.println(p+":"+params.get(p));
            sb.append(p).append("=").append(params.get(p)).append("&");
        }
        sb.append("apikey=").append(key);
        System.out.println(sb);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        System.out.println(sign);
        params.put("sign", sign);

        //开始请求
        String sr = HttpClient4.doPost(URL, params);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("errno");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 0) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);

            dealOrderExtra(false, directChargingOrderDto);
        }
        directChargingOrderDto1.setUpName(APIUP.YF_PHONE.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);

        return sr;
    }

    @Override
    public String yfCallBack(YFCallBack backInfo) {
        System.out.println("yf话费充值回调");
        System.out.println(backInfo);
        if (backInfo.getOut_trade_num() == null){
            return "success";
        }
        DirectChargingOrderDto directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(backInfo.getOut_trade_num());

        if (directChargingOrderDto == null){
            System.out.println("未找到订单");
            return "success";
        }

        try {
            if (backInfo.getState() == 1) {//交易成功
                directChargingOrderDto.setState(2);
                System.out.println("充值成功");
                dealOrderExtra(true, directChargingOrderDto);

            } else {//交易失败
                directChargingOrderDto.setState(4);
                dealOrderExtra(false, directChargingOrderDto);
            }
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            directChargingOrderDto.setState(4);
            dealOrderExtra(false, directChargingOrderDto);
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "success";
        }
    }


    @Override
    public String hxPhoneRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("hx话费充值");

        String URL="http://cdn.ltae.xyz/api/receiveOrderPhone";
        String account = directChargingOrderDto.getRechargeAccount();
        Integer amount = directChargingOrderDto.getRechargeAmount().intValue();
        String operator = directChargingOrderDto.getOperator();
        Integer type = null;
        String area = null;

        if (operator.length() > 2) {
            if (operator.endsWith("移动")) {
                type = 1;
            } else if (operator.endsWith("电信")) {
                type = 2;
            } else if (operator.endsWith("联通")) {
                type = 3;
            }
            area = operator.substring(0, operator.length()-2);
        }
        if (type == null || area == null){
            throw new Exception("暂不支持该运营商或地区");
        }

        String userid = "10133";

        String key = "f1f33b3c8160c165ecad1b62440be476";

        String no = directChargingOrderDto.getOrderId();

        String notify_rul = "http://47.110.9.136:8090/oilCardRecharge/hxCallBack";

        Map<String, Object> params = new HashMap<>();
        params.put("partner_id", userid);
        params.put("partner_order_no", no);
        params.put("account", account);
        params.put("amount", amount);
        params.put("type", type);
        params.put("area", area);
        params.put("charge_type", 0);
        params.put("notify_url", notify_rul);

        StringBuilder sb = new StringBuilder();
        sb.append(userid).append(no).append(account).append(amount).append(type).append(area).append(0).append(notify_rul).append(key);
        System.out.println(sb);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8");
        System.out.println(sign);
        params.put("sign", sign);

        //开始请求
        String sr = HttpClient4.doPost(URL, params);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 1) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);

            dealOrderExtra(false, directChargingOrderDto);
        }
        directChargingOrderDto1.setUpName(APIUP.HX_PHONE.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);

        return sr;
    }

    @Override
    public String hxCallBack(HXCallBack backInfo) {
        System.out.println("hx话费充值回调");
        System.out.println(backInfo);
        if (backInfo.getPartner_order_no() == null){
            return "success";
        }
        DirectChargingOrderDto directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(backInfo.getPartner_order_no());

        if (directChargingOrderDto == null){
            System.out.println("未找到订单");
            return "success";
        }

        try {
            if (backInfo.getStatus().equals("2")) {//交易成功
                directChargingOrderDto.setState(2);
                System.out.println("充值成功");
                dealOrderExtra(true, directChargingOrderDto);
            } else if (backInfo.getStatus().equals("1")) { // 部分成功
                directChargingOrderDto.setState(21);
            } else {//交易失败
//                if (directChargingOrderDto.getState() == 21) {
//                    directChargingOrderDto.setState(22);
//                } else {
//                    directChargingOrderDto.setState(4);
//                    dealOrderExtra(false, directChargingOrderDto);
//                }
                directChargingOrderDto.setState(22);
            }
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            directChargingOrderDto.setState(4);
            dealOrderExtra(false, directChargingOrderDto);
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "success";
        }
    }

    private static final Map<Integer, Integer> jhAmountMap = new HashMap<>();

    static {
        jhAmountMap.put(500, 9100661);
    }
    @Override
    public String jhOilRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("jh油卡充值");

        String URL="http://payapi.eyunpay.com/open/api/jy/recharge";

        String appid = "WP1678939686678";

        String phone = directChargingOrderDto.getRechargeAccount();

        Integer amount = directChargingOrderDto.getRechargeAmount().intValue() * 100;

        String key = "BdeDCWalkl1OrFc5zbY24IRbUDIN4qnF";

        String orderId = directChargingOrderDto.getOrderId();

        String asynchUrl = "http://47.110.9.136:8090/oilCardRecharge/jhCallBack";

        //设置请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appid);
        params.put("orderId", orderId);
        params.put("phone", phone);
        params.put("amount", amount);
        params.put("asynchUrl", asynchUrl);

        StringBuilder sb = new StringBuilder();
        TreeSet<String> sortedSet = new TreeSet<>(params.keySet());
        for (String p : sortedSet) {
            System.out.println(p+":"+params.get(p));
            sb.append(p).append("=").append(params.get(p)).append("&");
        }
        sb.append("key=").append(key);
        System.out.println(sb);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8");
        params.put("sign", sign);
        //开始请求
        JSONObject json = JSONObject.fromObject(params);
        System.out.println(json);
        String sr = HttpClient4.doPostJson(URL, json);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println(jsonObject);
        Integer code = (Integer) jsonObject.get("code");

        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (code == 0) {
            directChargingOrderDto1.setState(5);
        } else {
            directChargingOrderDto1.setState(4);

            dealOrderExtra(false, directChargingOrderDto);
        }
        directChargingOrderDto1.setUpName(APIUP.JH_OIL.value);
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);

        return sr;
    }

    @Override
    public String jhCallBack(JHCallBack backInfo) {
        System.out.println("jh充值回调");
        System.out.println(backInfo);
        if (backInfo.getOrderId() == null){
            return "success";
        }
        DirectChargingOrderDto directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(backInfo.getOrderId());

        if (directChargingOrderDto == null){
            System.out.println("未找到订单");
            return "success";
        }

        try {
            if (backInfo.getStatus() == 2) {//交易成功
                directChargingOrderDto.setState(2);
                System.out.println("充值成功");
                dealOrderExtra(true, directChargingOrderDto);
            } else if (backInfo.getStatus() == 3) {
                directChargingOrderDto.setState(4);
                System.out.println("充值失败");
                dealOrderExtra(false, directChargingOrderDto);
            } else {
                return "success";
            }
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            directChargingOrderDto.setState(4);
            dealOrderExtra(false, directChargingOrderDto);
            boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
            return "success";
        }
    }

    @Override
    public void dealOrderExtra(boolean success, DirectChargingOrderDto directChargingOrderDto){
        String orderId = directChargingOrderDto.getOrderId();
        DirectChargingOrderDto localOrder = oilCardRechargeMapperExtra.getOrder(orderId);
        if (localOrder != null){
            directChargingOrderDto = localOrder;
        }

        System.out.println("处理订单：");
        System.out.println(directChargingOrderDto.getOrderId());
        System.out.println(directChargingOrderDto.getThirdOrderId());
        System.out.println(directChargingOrderDto.getRechargeAccount());
        System.out.println(directChargingOrderDto.getRecordType());

        boolean isWithPet = directChargingOrderDto.getRecordType()==2;

        if (isWithPet && petrolSalesRecordsMapperExtra.selectInfoByOrgId(orderId) == null){
            return;
        }

        String notifyUrl = directChargingOrderDto.getCustomerOrderId();
        String thirdOrderId = directChargingOrderDto.getThirdOrderId();

        try {
            if (success){
                if (isWithPet){
                    petrolSalesRecordsMapperExtra.recharge(orderId);
                }
                if (notifyUrl != null){
                    ThirdOrderCallback back = new ThirdOrderCallback(200, "充值成功", thirdOrderId);
                    Map<String, Object> map = new TreeMap<>();
                    map.put("code", back.getCode());
                    map.put("msg", back.getMsg());
                    map.put("orderId", back.getOrderId());
                    String sign = SignUtil.signNoKey(map);
                    back.setSign(sign);
                    System.out.println(back);


                    System.out.println("通知第三方");
                    System.out.println(notifyUrl);
                    new Thread(() -> {
                        HttpClient4.doPost(notifyUrl, JSONObject.fromObject(back), 0);
                    }).start();
                }
                System.out.println("更变线下大客户充值订单成功");
            } else {
                if (isWithPet){
                    // 标记问题卡号
                    petrolSalesRecordsMapperExtra.updateMatterCard(orderId);
                }
                // 退款
                if (isWithPet){
                    userRechargeService.drawbackWithPet(orderId, false);
                } else {
                    userRechargeService.drawback(orderId);
                }

                if (notifyUrl != null){
                    ThirdOrderCallback back = new ThirdOrderCallback(400, "充值失败", thirdOrderId);
                    Map<String, Object> map = new TreeMap<>();
                    map.put("code", back.getCode());
                    map.put("msg", back.getMsg());
                    map.put("orderId", back.getOrderId());
                    String sign = SignUtil.signNoKey(map);
                    back.setSign(sign);
                    System.out.println(back);

                    System.out.println("通知第三方");
                    System.out.println(notifyUrl);

                    new Thread(() -> {
                        HttpClient4.doPost(notifyUrl, JSONObject.fromObject(back), 0);
                    }).start();
                }
                System.out.println("更变线下大客户充值订单成功");
            }
        } catch (Exception e){
            System.out.println("更新大客户订单失败");
            e.printStackTrace();
            if (notifyUrl != null){
                ThirdOrderCallback back = new ThirdOrderCallback(400, "充值失败", thirdOrderId);
                System.out.println(back);
                Map<String, Object> map = new TreeMap<>();
                map.put("code", back.getCode());
                map.put("msg", back.getMsg());
                map.put("orderId", back.getOrderId());
                String sign = SignUtil.signNoKey(map);
                back.setSign(sign);
                System.out.println("通知第三方");
                System.out.println(notifyUrl);
                new Thread(() -> {
                    HttpClient4.doPost(notifyUrl, JSONObject.fromObject(back), 0);
                }).start();
            }
        }
    }



    //    插入订单
    @Override
    public boolean insertOilCardOrder(DirectChargingOrderDto directChargingOrderDto) {
        boolean insertRecords = false;
        DirectChargingOrderDto directChargingOrder = new DirectChargingOrderDto();
        directChargingOrder.setUserId(directChargingOrderDto.getUserId());
        directChargingOrder.setOrderId(directChargingOrderDto.getOrderId());
        directChargingOrder.setThirdOrderId(directChargingOrderDto.getThirdOrderId());
        if (directChargingOrder.getOrderId() == null){
            directChargingOrder.setOrderId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        }
        directChargingOrder.setRechargeAmount(directChargingOrderDto.getRechargeAmount());
        // 2 油卡 8 话费
        directChargingOrder.setRecordType(2);
        if (directChargingOrderDto.getRecordType()!=null){
            directChargingOrder.setRecordType(directChargingOrderDto.getRecordType());
        }
        directChargingOrder.setPaymentMethod(1);
        directChargingOrder.setRealPrice(directChargingOrderDto.getRealPrice());
        directChargingOrder.setState(directChargingOrderDto.getState());
        if (directChargingOrder.getState() == null){
            directChargingOrder.setState(1);
        }
        directChargingOrder.setRechargeAccount(directChargingOrderDto.getRechargeAccount());
        directChargingOrder.setCardholder(directChargingOrderDto.getCardholder());
        directChargingOrder.setCustomerNumber(directChargingOrderDto.getCustomerNumber());
        directChargingOrder.setCustomerOrderId(directChargingOrderDto.getCustomerOrderId());

        insertRecords=oilCardRechargeMapperExtra.insertOilOrder(directChargingOrder)>0;

        return insertRecords;
    }

    @Override
    public boolean insertPhoneOrder(DirectChargingOrderDto directChargingOrderDto) {
        boolean insertRecords = false;
        DirectChargingOrderDto directChargingOrder = new DirectChargingOrderDto();
        directChargingOrder.setUserId(directChargingOrderDto.getUserId());
        directChargingOrder.setOrderId(directChargingOrderDto.getOrderId());
        directChargingOrder.setThirdOrderId(directChargingOrderDto.getThirdOrderId());
        if (directChargingOrder.getOrderId() == null){
            directChargingOrder.setOrderId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        }
        directChargingOrder.setRechargeAmount(directChargingOrderDto.getRechargeAmount());
        // 2 油卡 8 话费
        directChargingOrder.setRecordType(8);
        if (directChargingOrderDto.getRecordType()!=null){
            directChargingOrder.setRecordType(directChargingOrderDto.getRecordType());
        }
        directChargingOrder.setPaymentMethod(1);
        directChargingOrder.setRealPrice(directChargingOrderDto.getRealPrice());
        directChargingOrder.setState(directChargingOrderDto.getState());
        if (directChargingOrder.getState() == null){
            directChargingOrder.setState(1);
        }
        directChargingOrder.setRechargeAccount(directChargingOrderDto.getRechargeAccount());
        directChargingOrder.setCustomerNumber(directChargingOrderDto.getCustomerNumber());
        directChargingOrder.setCustomerOrderId(directChargingOrderDto.getCustomerOrderId());
        directChargingOrder.setOperator(getOperator(directChargingOrder.getRechargeAccount()));

        insertRecords=oilCardRechargeMapperExtra.insertOilOrder(directChargingOrder)>0;

        return insertRecords;
    }

    private String getOperator(String phone){
        if (phone == null || phone.equals("")){
            return null;
        }
        String url = "https://h5.10010.wiki/Shopping/Refills/GetPhoneNameArea";
        String params = "phone=" + phone;
        try {
            return HttpRequest.httpRequestGet(url, params);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String onlinemd5(OnlineorderDto onlineorderDto) {
        String result = onlineorderDto.getAppId()+"667cadbb-c0c5-40a4-bd05-ad2855e75143"+onlineorderDto.getGasUserid()+onlineorderDto.getOrdersn()+onlineorderDto.getCardnum()+onlineorderDto.getGasMobile();
        return MD5Util.getMD5Code(result);
    }

    private String phonemd5(TelorderDto telorderDto) {
        String result = telorderDto.getAppId()+"667cadbb-c0c5-40a4-bd05-ad2855e75143"+telorderDto.getPhoneno()+telorderDto.getCardnum()+telorderDto.getOrdersn();
        return MD5Util.getMD5Code(result);
    }

    private boolean isCorrectDataH5(Map<String, String> params) {

        // 验证app_id是否一致
        if (!params.get("app_id").equals(AliPayConfig.app_wap_id)) {
            return false;
        }

        // 判断交易状态是否为TRADE_SUCCESS
        if (!params.get("trade_status").equals("TRADE_SUCCESS")) {
            return false;
        }
        return true;
    }

    public Map<String, String>  parseOrder(Map<String, String> params, Map requestParams){
        //解读相应的信息
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            if (name.equals("fund_bill_list")) {
                valueStr = valueStr.replace("&quot;", "\"");
            }
            params.put(name, valueStr);
        }
        return params;
    }

    public void directFanyong(DirectChargingOrderDto directChargingOrderDto){
        System.out.println("直充返佣進入方法");
        try {
            directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(directChargingOrderDto.getOrderId());
            if (!fanyongLogService.isContainFanyongLog(directChargingOrderDto.getOrderId())){
                String userId = directChargingOrderDto.getUserId();
                Double actualPayment = directChargingOrderDto.getRechargeAmount();
                Double money = directChargingOrderDto.getRealPrice();
                if (actualPayment == null && money!=null){
                    actualPayment = money;
                }
                String orgId = directChargingOrderDto.getOrderId();
                boolean isSucceed = fanYongService.beginFanYong(7, "", userId, money, actualPayment, orgId);
                System.out.println("返佣"+isSucceed + " " + directChargingOrderDto.getOrderId());
            } else {
                System.out.println("已存在返佣记录  " + directChargingOrderDto.getOrderId());
            }
        } catch (Exception e){
            System.out.println("返佣失败");
            e.printStackTrace();
        }

    }

    @Override
    public JSONResult  getPhoneOrderState(DirectChargingOrderDto directChargingOrderDto){
        return null;

    }

    @Override
    public JSONResult  getOilOrderState(DirectChargingOrderDto directChargingOrderDto){
        String URL="https://huafei.renduoduo2019.com/api/sinopec/ordersta";
        //人多多的订单号（由我方生成）
        String ordersn = directChargingOrderDto.getOrderId();

        // appId
        String appId = "7192701d-bdb6-4ad7-a558-247b4331bf86";

        String appSecret = "667cadbb-c0c5-40a4-bd05-ad2855e75143";

        String string = appId + appSecret + ordersn;

        // sign
        String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

        //设置请求参数
        String params = "ordersn=" + ordersn +
                "&appId=" + appId +
                "&sign=" + sign;

        System.out.println(params);

        //开始请求
        String sr= HttpRequest.httpRequestGet(URL, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println("油卡充值状态");
        System.out.println(sr);
        System.out.println(jsonObject);
        int begin = sr.indexOf("orderStatus");
        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (Integer.parseInt(sr.substring(begin+14, begin+15)) == 1) {
            directChargingOrderDto1.setState(5);
        } else if (Integer.parseInt(sr.substring(begin+14, begin+15)) == 2) {
            directChargingOrderDto1.setState(2);

            directFanyong(directChargingOrderDto);

        } else if (Integer.parseInt(sr.substring(begin+14, begin+15)) == 3) {
            directChargingOrderDto1.setState(4);
        } else {
            directChargingOrderDto1.setState(4);
        }
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
        System.out.println(directChargingOrderDto1.toString());
        System.out.println("油卡状态");
        System.out.println(sr);
        return new JSONResult("状态查询成功", 200);
    }

    @Override
    public JSONResult isPhoneRecharge(DirectChargingOrderDto directChargingOrderDto){
        String URL="https://huafei.renduoduo2019.com/api/mobile/telcheck";

        // 电话号码
        String phoneno = directChargingOrderDto.getUserAccount();

        // 金额
        Integer cardnum = directChargingOrderDto.getRealPrice().intValue();

        // appId
        String appId = "7192701d-bdb6-4ad7-a558-247b4331bf86";

        String appSecret = "667cadbb-c0c5-40a4-bd05-ad2855e75143";

        String string = appId + appSecret + phoneno + String.valueOf(cardnum);
        // sign
        String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

        //设置请求参数
        String params = "cardnum=" + cardnum +
                "&phoneno=" + phoneno +
                "&appId=" + appId +
                "&sign=" + sign;

        System.out.println(params);

        //开始请求
        String sr= HttpRequest.httpRequestGet(URL, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println("是否可以充值");
        System.out.println(sr);
        System.out.println(jsonObject);
        int begin = sr.indexOf("code");
        if (sr.substring(begin+6, begin+7).equals("0")) {
            return new JSONResult("可以充值", 200);
        } else {
            return new JSONResult("无法充值", 500);
        }
    }

    @Override
    public com.alibaba.fastjson.JSONObject WeChatRechargeDirect(DirectChargingOrderDto directChargingOrderDto) {

        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");
        /**
         * 生成起调参数串——返回给app（微信的支付订单）
         */
        //订单标识


        String nonceStrTemp = WeChatUtils.getRandomStr();

        //支付的金额
//        Double money=backMoney( petrol,petrolInputDTO);
//        Double rechargeAmount = directChargingOrderDto.getRealPrice();

        // 获取金额校验
        Double rechargeAmount = oilCardRechargeMapperExtra.getDirectRechargeAmount(directChargingOrderDto.getCommodityId());

        if (rechargeAmount == null) {
            System.out.println("直冲无此价格");
            return null;
        }
//        if ((oilCardRechargeMapperExtra.getDirectRechargeAmount(directChargingOrderDto.getRealPrice())==null)) {
//            System.out.println("直充无此价格");
//            return null;
//        }

        // 积分校验
        int integralAmount = 0;
        if (directChargingOrderDto.getIntegralAmount() <= oilCardRechargeMapperExtra.getMaxIntegralAmount(directChargingOrderDto.getCommodityId())) {
            integralAmount = directChargingOrderDto.getIntegralAmount();
        } else {
            System.out.println("积分超过本商品最高抵扣额度");
            return null;
        }
        Double amount = rechargeAmount - integralAmount;

        directChargingOrderDto.setRechargeAmount(amount);
        directChargingOrderDto.setRealPrice(rechargeAmount);
        // userId
        String userId = directChargingOrderDto.getUserId();
        //直充类型
        Integer recordType = directChargingOrderDto.getRecordType();

        String userAccount = directChargingOrderDto.getUserAccount();

        Integer isBrowser = directChargingOrderDto.getIsBrowser();

//        Integer integralAmount = directChargingOrderDto.getIntegralAmount();

        String cardNum = "";

        String openId = directChargingOrderDto.getOpenId();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatH5ParameterConfig.getParametersDirect(nonceStrTemp,orgId, amount, rechargeAmount, recordType,userAccount, isBrowser, userId, integralAmount,openId,directChargingOrderDto.getCommodityId());
        boolean insertSalesRecords = false;
        if (recordType == 1){
            insertSalesRecords= insertPhonePillRecords(directChargingOrderDto,orgId);
            System.out.println("插入成功");
        }else{
            directChargingOrderDto.setCardholder(directChargingOrderDto.getUserAccount());
            directChargingOrderDto.setUserAccount(cardNum);
            insertSalesRecords= insertPhonePillRecords(directChargingOrderDto,orgId);
        }
        return WeChatH5ParameterConfig.getSign(parameters, nonceStrTemp);
    }

    @Override
    public String wechatPayReturn(HttpServletRequest request, String consumptionType) {
        try {
            ServletInputStream in = request.getInputStream();
            String resxml = WeChatFileUtil.inputStream2String(in);
            Map<String, Object> restmap = WeChatUtils.xml2Map(resxml);
            if ("SUCCESS".equals(restmap.get("result_code"))) {
                // 订单支付成功 业务处理
                if (checkSign(restmap)) {
                    // 进行业务处理
                    Object[] param = { restmap };
                    Map<String, Integer> result = refuelingCard.WeChatPayBack(param,consumptionType);
                    if (result.get("success") == 1) {
                        return getSuccess();
                    } else {
                        return AlipayConfig.response_fail;
                    }
                } else {
                    return AlipayConfig.response_fail;
                }
            } else {
                return AlipayConfig.response_fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getSuccess();
        }
    }

    // 微信异步通知成功
    public String getSuccess() {
        SortedMap<String, Object> respMap = new TreeMap<>();
        respMap = new TreeMap<String, Object>();
        respMap.put("return_code", "SUCCESS"); // 响应给微信服务器
        respMap.put("return_msg", "OK");
        String resXml = WeChatUtils.map2xml(respMap);
        return resXml;
    }

    // 验证签名（微信）
    public boolean checkSign(Map<String, Object> restmap) {
        String sign = (String) restmap.get("sign"); // 返回的签名
        restmap.remove("sign");
        SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
        for (Map.Entry<String, Object> entry : restmap.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        //爱动key
        String signNow = WeChatUtils.createSign(characterEncoding, sortedMap);
        //人多多key
        String signNowRdd = WeChatUtils.createRddSign(characterEncoding, sortedMap);
        if (sign.equals(signNow)||sign.equals(signNowRdd)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取电话号码
    @Override
    public String getAccount(String userId) {
        return oilCardRechargeMapperExtra.getAccount(userId);
    }

    @Override
    public Workbook exportOilCardRecord(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        List<DirectChargingOrderDto> list = oilCardRechargeMapperExtra.getAllOnceOrderInfoList(directChargingOrderDto);
        if(list==null||list.size()==0){
            return getOilCardRecord(null,directChargingOrderDto);
        }
        return getOilCardRecord(list, directChargingOrderDto);
    }

    @Override
    public JSONResult automaticSubmitPhone(AutoDirectDto autoDirectDto){

        return null;

    }

    @Override
    public JSONResult automaticSubmitPhone(DictInputDTO phone) {
        System.out.println("传入的automaticSubmit为" + phone.getName() + " : " + phone.getContent());
        System.out.println("话费自动");
        int result = dictMapperExtra.updateDictByName(phone);
        if (result > 0 ) {
            String state = dictMapperExtra.selectDictByName("automatic_submit_phone").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("automatic_phone_time").getContent());
                AutoTimerTask task = new AutoTimerTask("auto_phone_sub") {
                    @Override
                    public void execute() {
                        autoSubmitPhoneM(APIUP.CHENXIE_PHONE);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("auto_phone_sub", true);
                timeTaskManager.addTimerTask("auto_phone_sub", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("auto_phone_sub", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult automaticSubmitOilCard(DictInputDTO oil) {
        System.out.println("传入的automaticSubmit为" + oil.getName() + " : " + oil.getContent());
        System.out.println("油卡自动");
        int result = dictMapperExtra.updateDictByName(oil);
        if (result > 0 ) {
            String state = dictMapperExtra.selectDictByName("automatic_submit_oilcard").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("automatic_oilcard_time").getContent());
                AutoTimerTask task = new AutoTimerTask("auto_oilcard_sub") {
                    @Override
                    public void execute() {
                        autoSubmitOilCardM(APIUP.CHENXIE_OIL);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("auto_oilcard_sub", true);
                timeTaskManager.addTimerTask("auto_oilcard_sub", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("auto_oilcard_sub", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult automaticSubmitPhoneFast(DictInputDTO phone) {
        System.out.println("传入的automaticSubmit为" + phone.getName() + " : " + phone.getContent());
        System.out.println("话费自动");
        int result = dictMapperExtra.updateDictByName(phone);
        if (result > 0 ) {
            String state = dictMapperExtra.selectDictByName("automatic_submit_phone_fast").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("automatic_phone_time_fast").getContent());
                AutoTimerTask task = new AutoTimerTask("auto_phone_sub_fast") {
                    @Override
                    public void execute() {
                        autoSubmitPhoneM(APIUP.ANDA888_PHONE);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("auto_phone_sub_fast", true);
                timeTaskManager.addTimerTask("auto_phone_sub_fast", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("auto_phone_sub_fast", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult automaticSubmitOilCardFast(DictInputDTO oil) {
        System.out.println("传入的automaticSubmit为" + oil.getName() + " : " + oil.getContent());
        System.out.println("油卡自动");
        int result = dictMapperExtra.updateDictByName(oil);
        if (result > 0 ) {
            String state = dictMapperExtra.selectDictByName("automatic_submit_oilcard_fast").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("automatic_oilcard_time_fast").getContent());
                AutoTimerTask task = new AutoTimerTask("auto_oilcard_sub_fast") {
                    @Override
                    public void execute() {
                        autoSubmitOilCardM(APIUP.ANDA888_OIL);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("auto_oilcard_sub_fast", true);
                timeTaskManager.addTimerTask("auto_oilcard_sub_fast", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("auto_oilcard_sub_fast", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult automaticSubmitPhoneYF(DictInputDTO phone) {
        System.out.println("传入的automaticSubmit为" + phone.getName() + " : " + phone.getContent());
        System.out.println("话费自动");
        int result = dictMapperExtra.updateDictByName(phone);
        if (result > 0 ) {
            System.out.println("yf话费自动");
            String state = dictMapperExtra.selectDictByName("atp_yf").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("atp_yf_time").getContent());
                AutoTimerTask task = new AutoTimerTask("atp_yf_task") {
                    @Override
                    public void execute() {
                        autoSubmitPhoneM(APIUP.YF_PHONE);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("atp_yf_task", true);
                System.out.println("yf话费自动开始");
                timeTaskManager.addTimerTask("atp_yf_task", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("atp_yf_task", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult automaticSubmitPhoneHX(DictInputDTO phone) {
        System.out.println("传入的automaticSubmit为" + phone.getName() + " : " + phone.getContent());
        System.out.println("话费自动");
        int result = dictMapperExtra.updateDictByName(phone);
        if (result > 0 ) {
            System.out.println("hx话费自动");
            String state = dictMapperExtra.selectDictByName("atp_hx").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("atp_hx_time").getContent());
                AutoTimerTask task = new AutoTimerTask("atp_hx_task") {
                    @Override
                    public void execute() {
                        autoSubmitPhoneM(APIUP.HX_PHONE);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("atp_hx_task", true);
                System.out.println("hx话费自动开始");
                timeTaskManager.addTimerTask("atp_hx_task", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("atp_hx_task", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult automaticSubmitPhoneJH(DictInputDTO phone) {
        System.out.println("传入的automaticSubmit为" + phone.getName() + " : " + phone.getContent());
        System.out.println("话费自动");
        int result = dictMapperExtra.updateDictByName(phone);
        if (result > 0 ) {
            System.out.println("jh话费自动");
            String state = dictMapperExtra.selectDictByName("atp_jh").getContent();
            if (state.equals("1")) {
                Long rate = Long.parseLong(dictMapperExtra.selectDictByName("atp_jh_time").getContent());
                AutoTimerTask task = new AutoTimerTask("atp_jh_task") {
                    @Override
                    public void execute() {
                        autoSubmitOilCardM(APIUP.JH_OIL);
                    }
                };
                task.setPeriod(rate);
                timeTaskManager.deleteTimerTaskByCode("atp_jh_task", true);
                System.out.println("jh话费自动开始");
                timeTaskManager.addTimerTask("atp_jh_task", task);
            } else {
                timeTaskManager.deleteTimerTaskByCode("atp_jh_task", true);
            }
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    private void autoSubmitOilCardM(APIUP up){
        DirectChargingOrderDto q = new DirectChargingOrderDto();
        System.out.println("油卡自动充值开始");

        q.setState(1);
        q.setRecordType(2);
        List<DirectChargingOrderDto> list = oilCardRechargeMapperExtra.getAllOnceOrderInfoList(q);
        if (list == null || list.isEmpty()){
            return;
        }
        DirectChargingOrderDto order = list.get(list.size() - 1);
        try {
            switch (up) {
                case ANDA888_OIL:
                    fastOilOrderSubmit(order);
                    break;
                case CHENXIE_OIL:
                    chenxieOilRechargeSubmit(order);
                    break;
                case JH_OIL:
                    jhOilRechargeSubmit(order);
                    break;
            }
        } catch (Exception e) {
            order.setState(4);
            oilCardRechargeMapperExtra.updateOrderState(order);
            dealOrderExtra(false, order);
            e.printStackTrace();
        }
    }

    private void autoSubmitPhoneM(APIUP up){
        DirectChargingOrderDto q = new DirectChargingOrderDto();
        System.out.println("话费自动充值开始");
        q.setState(1);
        q.setRecordType(8);
        List<DirectChargingOrderDto> list = oilCardRechargeMapperExtra.getAllOnceOrderInfoList(q);
        if (list == null || list.isEmpty()){
            return;
        }
        DirectChargingOrderDto order = list.get(list.size() - 1);
        try {
            switch (up) {
                case ANDA888_PHONE:
                    fastPhoneOrderSubmit(order);
                    break;
                case CHENXIE_PHONE:
                    chenxiePhoneRechargeSubmit(order);
                    break;
                case YF_PHONE:
                    yfPhoneRechargeSubmit(order);
                    break;
                case HX_PHONE:
                    hxPhoneRechargeSubmit(order);
                    break;
            }
        } catch (Exception e) {
            order.setState(4);
            oilCardRechargeMapperExtra.updateOrderState(order);
            dealOrderExtra(false, order);
            e.printStackTrace();
        }
    }



    public List<DirectChargingOrderDto> getPartOrderInfoList(DirectChargingOrderDto directChargingOrderDto) {
        System.out.println("进入查询" + directChargingOrderDto);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getPartOrderInfoList(directChargingOrderDto);
        System.out.println(withdrawList.size());
        return withdrawList;
    }

    @Override
    public JSONResult getSelectPhoneOrderState(SelectOrderDto selectOrderDto) {
        if (selectOrderDto.getOrderId() == null) {
            return new JSONResult("更新成功", 200);
        }
        for (int i = 0; i < selectOrderDto.getOrderId().length; i++) {
            DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
            directChargingOrderDto.setOrderId(selectOrderDto.getOrderId()[i]);
            getPhoneOrderState(directChargingOrderDto);
            if (i == selectOrderDto.getOrderId().length - 1) {
                return new JSONResult("更新成功", 200);
            }
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult getSelectOilOrderState(SelectOrderDto selectOrderDto) {
        for (int i = 0; i < selectOrderDto.getOrderId().length; i++) {
            DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
            directChargingOrderDto.setOrderId(selectOrderDto.getOrderId()[i]);
            getOilOrderState(directChargingOrderDto);
            if (i == selectOrderDto.getOrderId().length - 1) {
                return new JSONResult("更新成功", 200);
            }
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public int importData(MultipartFile file, Integer recordType) throws Exception {
        int result = 0;
        InputStream inputStream = file.getInputStream();
        List<DirectChargingOrderDto> deliveryList = null;
        Map<String, DirectChargingOrderDto> deliveryMap = new HashMap<>();
        System.out.println("读取Excel");
        System.out.println(file.getOriginalFilename());
        deliveryList = DirectChargingSystemDelivery.readExcel(file.getOriginalFilename(), inputStream);
        System.out.println(deliveryList);
        if(deliveryList != null){
            for (DirectChargingOrderDto directChargingOrderDto : deliveryList){
//                result = oilCardRechargeMapperExtra.importState(directChargingOrderDto);
//
//                if (directChargingOrderDto.getState() == 2){
//                    directFanyong(directChargingOrderDto);
//                }
                if (directChargingOrderDto.getRecordType() == 8) {
                    result += this.insertPhoneOrder(directChargingOrderDto) ? 1 : 0;
                } else {
                    result += this.insertOilCardOrder(directChargingOrderDto) ? 1 : 0;
                }
            }
        }
        return result;
    }

    @Override
    public int importUpdate(MultipartFile file, Integer recordType) throws Exception {
        InputStream inputStream = file.getInputStream();
        List<DirectChargingOrderDto> deliveryList = null;
        Map<String, DirectChargingOrderDto> deliveryMap = new HashMap<>();
        System.out.println("读取Excel");
        System.out.println(file.getOriginalFilename());
        deliveryList = RechargeOrderUpdateImportHelper.readExcel(file.getOriginalFilename(), inputStream);
        if(deliveryList != null){
            List<DirectChargingOrderDto> finalDeliveryList = deliveryList;
            new Thread(() -> {
                for (DirectChargingOrderDto orderDto : finalDeliveryList){
                    try {
                        if (orderDto.getOrderId() == null || orderDto.getOrderId().equals("")){
                            DirectChargingOrderDto q = new DirectChargingOrderDto();
                            q.setState(5);
                            q.setRecordType(8);
                            q.setRechargeAccount(orderDto.getRechargeAccount());
                            List<DirectChargingOrderDto> list = oilCardRechargeMapperExtra.getAllOnceOrderInfoList(q);
                            if (!list.isEmpty()) {
                                orderDto.setOldOrderId(list.get(0).getOrderId());
                                directChargingOrderService.updateRecord(orderDto);
                            }
                        } else {
                            DirectChargingOrderDto o = oilCardRechargeMapperExtra.getOrder(orderDto.getOrderId());
                            if (o != null) {
                                orderDto.setOldOrderId(o.getOrderId());
                                directChargingOrderService.updateRecord(orderDto);
                            }
                        }
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }).start();
        }
        return 0;
    }

    @Override
    public JSONResult submitSelectState(SelectOrderDto selectOrderDto) {
        int count = 0;
        System.out.println(selectOrderDto);
        for (int i = 0; i < selectOrderDto.getOrderId().length; i++) {
            DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
            directChargingOrderDto.setOrderId(selectOrderDto.getOrderId()[i]);
            directChargingOrderDto.setState(selectOrderDto.getState());
            int result = oilCardRechargeMapperExtra.importState(directChargingOrderDto);

            if (result > 0){
                if (directChargingOrderDto.getState() == 2){
                    dealOrderExtra(true, directChargingOrderDto);
                    count++;
                } else if (directChargingOrderDto.getState() == 4){
                    dealOrderExtra(false, directChargingOrderDto);
                    count++;
                }
            }

        }
        return new JSONResult("更新成功数量："+count, 200);
    }

    @Override
    public boolean test() {
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();

        directChargingOrderDto.setRechargeAmount(50.00);
        directChargingOrderDto.setOrderId("641398771443602425");
        directChargingOrderDto.setUserAccount("18523552621");

        String URL = "https://api.36duojing.com/v1/mobile/sloworder";

        //平台编码
        String appKey = "30000503";
        //密匙
        String appSecret = "Rw4lEFfnJqRnjKVuJuLp1rdnJyJ91S1-";
        //订单号
        String orderId = directChargingOrderDto.getOrderId();
        //手机号
        String mobile = directChargingOrderDto.getUserAccount();
        //金额
        Double amount = directChargingOrderDto.getRechargeAmount();
        //第三方接口
        String notifyUrl = AliPayConfig.DirectPhone_url;
        //sign
        TreeMap map = new TreeMap();
        map.put("appKey",appKey);
        map.put("orderId",orderId);
        map.put("mobile",mobile);
        map.put("amount",new DecimalFormat("###0.00").format(amount));
        map.put("notifyUrl",notifyUrl);
        String string = "";
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            if (!string.equals("")){
                string+="&";
            }
            string += iterator.next();
        }
        string+="&key=" + appSecret;
        System.out.println(string);
        String sign = MD5Util.MD5Encode(string,"UTF-8").toUpperCase();
        System.out.println(sign);

        String params = "appKey=" + appKey +
                "&orderId=" + orderId +
                "&mobile=" + mobile +
                "&amount=" + new DecimalFormat("###0.00").format(amount) +
                "&notifyUrl=" + notifyUrl +
                "&sign=" + sign;

        System.out.println(params);
        //开始请求
        String sr= HttpRequest.httpRequestPost(URL, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println("话费充值");
        System.out.println(jsonObject);
        int begin = sr.indexOf("code");
        int end = sr.indexOf("result");
        String result_code = jsonObject.getString("result_code");
        int return_code = jsonObject.getInt("return_code");
        System.out.println(jsonObject.get("data"));
        return false;
    }

    private Workbook getOilCardRecord(List<DirectChargingOrderDto> list, DirectChargingOrderDto directChargingOrderDto) throws Exception{
        String[] consumptionHead = SystemConstants.OIL_CARD_RECHARGE_EXCEL_HEAD;
        Workbook workbook = null;
        DirectChargingOrderDto directChargingOrderDto1 = oilCardRechargeMapperExtra.getTheStatics(directChargingOrderDto);
        Double totalRealPrice = directChargingOrderDto1.getTotalRealPrice();
        Double totalRechargeAmount = oilCardRechargeMapperExtra.getTheStatics(directChargingOrderDto).getTotalRechargeAmount();
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无订单记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < consumptionHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(consumptionHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getOrderId());
            row.createCell(count++).setCellValue(list.get(i).getThirdOrderId());
            row.createCell(count++).setCellValue(list.get(i).getRechargeAccount());
            row.createCell(count++).setCellValue(list.get(i).getCardholder());
            if (list.get(i).getRealPrice() != null){
                row.createCell(count++).setCellValue(list.get(i).getRealPrice());
            }else {
                row.createCell(count++).setCellValue("");
            }
            if (list.get(i).getRechargeAmount() != null){
                row.createCell(count++).setCellValue(list.get(i).getRechargeAmount());
            }else {
                row.createCell(count++).setCellValue("");
            }

            if (1 == list.get(i).getState()){
                row.createCell(count++).setCellValue("待充值");
            }else if (0 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("未支付");
            }else if (2 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("已充值");
            }
            else if (3 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("支付失败");
            }
            else if (4 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("充值失败");
            }
            else if (5 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("充值中");
            }
            else if (6 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("已退款");
            }
            else if (7 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("当日超次数");
            }else if (8 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("携号转网");
            }else if (9 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("排队中");
            }else if (10 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("待充值（快充）");
            } else {
                row.createCell(count++).setCellValue("其他");
            }
            row.createCell(count++).setCellValue(list.get(i).getCustomerNumber());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateAt()));
            row.createCell(count++).setCellValue(list.get(i).getUpName());
            row.createCell(count++).setCellValue(list.get(i).getOperator());
        }
        int index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("充值金额：");
        if (totalRealPrice != null) {
            row.createCell(index++).setCellValue(totalRealPrice);
        } else {
            row.createCell(index++).setCellValue(0);
        }
        row.createCell(index++).setCellValue("支付金额：");
        if (totalRechargeAmount != null) {
            row.createCell(index++).setCellValue(totalRechargeAmount);
        } else {
            row.createCell(index++).setCellValue(0);
        }
        return workbook;
    }

}

class AutoBufferTimeTask extends AutoTimerTask{

    public AutoBufferTimeTask(String _taskCode) {
        super(_taskCode);
    }

    @Override
    public void execute() {
        System.out.println("开启自动提交订单");
    }
}
