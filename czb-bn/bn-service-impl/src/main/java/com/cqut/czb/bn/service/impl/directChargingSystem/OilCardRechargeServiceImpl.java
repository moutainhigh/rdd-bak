package com.cqut.czb.bn.service.impl.directChargingSystem;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.AliParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.AliPayConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.AlipayClientConfig;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OnlineorderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.TelorderDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.UserCardRelation;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PaymentProcess.BusinessProcessService;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.LuPayApiConfig;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OilCardRechargeServiceImpl implements OilCardRechargeService {
    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Autowired
    private BusinessProcessService refuelingCard;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type) {
        if (type != 1) {
            return oilCardRechargeMapperExtra.getOrderInfoList(userId, type);
        } else {
            return oilCardRechargeMapperExtra.getOilOrderInfoList(userId, type);
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
    public JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getAllUserCard(directChargingOrderDto);
        PageInfo<DirectChargingOrderDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult updateOrderState(DirectChargingOrderDto directChargingOrderDto) {
        int update = oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto);
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


//    直充支付
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

        //订单标识
        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");

        //支付的金额
//        Double money=backMoney( petrol,petrolInputDTO);
        Double rechargeAmount = directChargingOrderDto.getRealPrice();

        Double amount = directChargingOrderDto.getRechargeAmount();
        // userId
        String userId = directChargingOrderDto.getUserId();
        //直充类型
        Integer recordType = directChargingOrderDto.getRecordType();
        String userAccount = directChargingOrderDto.getUserAccount();
        String cardNum;
        if (recordType == 2){
            cardNum = directChargingOrderDto.getPetrolChinaPetrolNum();
        }else{
            cardNum = directChargingOrderDto.getSinopecPetrolNum();
        }
        request.setReturnUrl(AliPayConfig.Return_url);
        request.setBizModel(AliParameterConfig.getPhonePill(orderId,amount, rechargeAmount, userId, recordType,cardNum,userAccount));//支付订单
        request.setNotifyUrl(AliPayConfig.Direct_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //插入购买信息
        boolean insertPetrolSalesRecords= insertPhonePillRecords(directChargingOrderDto,orderId);
        System.out.println("新增直充充值记录完毕"+insertPetrolSalesRecords);
        return orderString;
    }

    //    插入订单
    public boolean insertPhonePillRecords(DirectChargingOrderDto directChargingOrderDto, String orderId) {
        DirectChargingOrderDto directChargingOrder = new DirectChargingOrderDto();
        directChargingOrder.setUserId(directChargingOrderDto.getUserId());
        directChargingOrder.setOrderId(orderId);
        directChargingOrder.setRechargeAmount(directChargingOrderDto.getRechargeAmount());
        directChargingOrder.setRecordType(directChargingOrderDto.getRecordType());
        directChargingOrder.setPaymentMethod(1);
        directChargingOrder.setRealPrice(directChargingOrderDto.getRealPrice());
        directChargingOrder.setState(0);
        boolean insertRecords=oilCardRechargeMapperExtra.insertOrder(directChargingOrder)>0;
        return insertRecords;
    }

    @Override
    public String aliPayReturn(HttpServletRequest request, String consumptionType) {
        // 获取支付宝POST过来反馈信息
        System.out.println(1);
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        params=parseOrder(params,requestParams);
        System.out.println("params"+params.toString());
        DirectChargingOrderDto directChargingOrderDto = getParams(params);
        System.out.println("directChargingOrderDto"+directChargingOrderDto.toString());
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
                        if (directChargingOrderDto.getRecordType()==1){
                            phoneRechargeSubmission(directChargingOrderDto);
                        }else{
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
        }
        System.out.println(userAccount);
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
        directChargingOrderDto.setOrderId(orgId);
        directChargingOrderDto.setRecordType(recordType);
        directChargingOrderDto.setUserAccount(userAccount);
        directChargingOrderDto.setPetrolChinaPetrolNum(cardNum);
        directChargingOrderDto.setRechargeAmount(rechargeAmount);
        directChargingOrderDto.setUpdateAt(new Date());
        return directChargingOrderDto;
    }

    public void phoneRechargeSubmission(DirectChargingOrderDto directChargingOrderDto){
//        System.out.println(3);
//        System.out.println(directChargingOrderDto);
//        String url = "https://huafei.renduoduo2019.com/api/mobile/telorder";
//        TelorderDto telorderDto = new TelorderDto();
//        telorderDto.setPhoneno(directChargingOrderDto.getUserAccount());
//        telorderDto.setOrdersn(directChargingOrderDto.getOrderId());
//        telorderDto.setCardnum(String.valueOf(directChargingOrderDto.getRechargeAmount()));
//        telorderDto.setAppId("7192701d-bdb6-4ad7-a558-247b4331bf86");
//        telorderDto.setSign(phonemd5(telorderDto));
//        System.out.println("telorderDto"+telorderDto.toString());
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, telorderDto, String.class);
//        String body = responseEntity.getBody();
//        int begin = body.indexOf("code");
//        int end = body.indexOf("result");
//        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
//        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
//        if (body.substring(begin+6, end-2) == "0") {
//            directChargingOrderDto1.setState(2);
//        } else {
//            directChargingOrderDto1.setState(4);
//        }
//        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
//        System.out.println("话费直冲");
//        System.out.println(body);

        String URL="https://huafei.renduoduo2019.com/api/mobile/telorder";
        //人多多的订单号（由我方生成）
        String ordersn = directChargingOrderDto.getOrderId();

        // 电话号码
        String phoneno = directChargingOrderDto.getUserAccount();

        // 金额
        Integer cardnum = directChargingOrderDto.getRechargeAmount().intValue();

        // appId
        String appId = "7192701d-bdb6-4ad7-a558-247b4331bf86";

        String appSecret = "667cadbb-c0c5-40a4-bd05-ad2855e75143";

        String string = appId + appSecret + phoneno + String.valueOf(cardnum) + ordersn;
        // sign
        String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

        //设置请求参数
        String params = "appId=" + appId +
                    "&phoneno=" + phoneno +
                    "&cardnum=" + cardnum +
                    "&ordersn=" + ordersn +
                    "&sign=" + sign;

        System.out.println(params);

        //开始请求
        String sr= HttpRequest.httpRequestPost(URL, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println("话费充值");
        System.out.println(sr);
        System.out.println(jsonObject);
        int begin = sr.indexOf("code");
        int end = sr.indexOf("result");
        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (sr.substring(begin+6, end-2) == "0") {
            directChargingOrderDto1.setState(2);
        } else {
            directChargingOrderDto1.setState(4);
        }
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
        System.out.println("话费直冲");
        System.out.println(sr);
    }

    public void onlineorderSubmission(DirectChargingOrderDto directChargingOrderDto){
//        String url = "https://huafei.renduoduo2019.com/api/sinopec/onlineorder";
//        OnlineorderDto onlineorderDto = new OnlineorderDto();
//        onlineorderDto.setGasUserid(directChargingOrderDto.getPetrolChinaPetrolNum());
//        onlineorderDto.setGasMobile(directChargingOrderDto.getUserAccount());
//        onlineorderDto.setOrdersn(directChargingOrderDto.getOrderId());
//        onlineorderDto.setCardnum(String.valueOf(directChargingOrderDto.getRechargeAmount()));
//        onlineorderDto.setAppId("7192701d-bdb6-4ad7-a558-247b4331bf86");
//        onlineorderDto.setSign(onlinemd5(onlineorderDto));
//        System.out.println(onlineorderDto);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, onlineorderDto, String.class);
//        String body = responseEntity.getBody();
//        int begin = body.indexOf("code");
//        int end = body.indexOf("result");
//        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
//        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
//        if (body.substring(begin+6, end-2) == "0") {
//            directChargingOrderDto1.setState(2);
//        } else {
//            directChargingOrderDto1.setState(4);
//        }
//        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
//        System.out.println("油卡直冲");
//        System.out.println(body);

        String URL="https://huafei.renduoduo2019.com/api/sinopec/onlineorder";
        //人多多的订单号（由我方生成）
        String ordersn = directChargingOrderDto.getOrderId();

        // 油卡号
        String gasUserid = directChargingOrderDto.getPetrolChinaPetrolNum();

        // 持卡人手机号
        String gasMobile = directChargingOrderDto.getUserAccount();

        // 金额
        Integer cardnum = directChargingOrderDto.getRechargeAmount().intValue();

        // appId
        String appId = "7192701d-bdb6-4ad7-a558-247b4331bf86";

        String appSecret = "667cadbb-c0c5-40a4-bd05-ad2855e75143";

        String string = appId + appSecret + gasUserid + ordersn + String.valueOf(cardnum) + gasMobile;

        // sign
        String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

        //设置请求参数
        String params = "gasUserid=" + gasUserid +
                "&gasMobile=" + gasMobile +
                "&ordersn=" + ordersn +
                "&cardnum=" + cardnum +
                "&appId=" + appId +
                "&sign=" + sign;

        System.out.println(params);

        //开始请求
        String sr= HttpRequest.httpRequestPost(URL, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);
        System.out.println("油卡充值");
        System.out.println(sr);
        System.out.println(jsonObject);
        int begin = sr.indexOf("code");
        int end = sr.indexOf("result");
        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        if (sr.substring(begin+6, end-2) == "0") {
            directChargingOrderDto1.setState(2);
        } else {
            directChargingOrderDto1.setState(4);
        }
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
        System.out.println("油卡直冲");
        System.out.println(sr);
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

    @Override
    public JSONResult  getPhoneOrderState(DirectChargingOrderDto directChargingOrderDto){
        String URL="https://huafei.renduoduo2019.com/api/mobile/ordersta";
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
        System.out.println("油卡充值");
        System.out.println(sr);
        System.out.println(jsonObject);
        int begin = sr.indexOf("orderStatus");
        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        directChargingOrderDto1.setState(Integer.parseInt(sr.substring(begin+14, begin+15)));
        oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
        System.out.println(directChargingOrderDto1.toString());
        System.out.println("油卡状态");
        System.out.println(sr);
        return new JSONResult("状态查询成功", 200);
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
        System.out.println("油卡充值");
        System.out.println(sr);
        System.out.println(jsonObject);
        int begin = sr.indexOf("orderStatus");
        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(directChargingOrderDto.getOrderId());
        directChargingOrderDto1.setState(Integer.parseInt(sr.substring(begin+14, begin+15)));
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
        if (sr.substring(begin+6, begin+7) == "0") {
            return new JSONResult("可以充值", 200);
        } else {
            return new JSONResult("无法充值", 500);
        }
    }

}
