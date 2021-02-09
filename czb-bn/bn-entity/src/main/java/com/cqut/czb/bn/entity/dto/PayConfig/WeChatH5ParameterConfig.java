package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.util.string.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class WeChatH5ParameterConfig {

    //统一获取签名,微信支付签名请求
    public static JSONObject getSign(SortedMap<String, Object> parameters, String nonceStrTemp){
        // 转为xml格式
        String info = WeChatUtils.map2xml(parameters);
        System.out.println(info);
        String orderList = null;//用于保存起调参数,
        orderList = WeChatHttpUtil.httpsRequest(WeChatH5PayConfig.URL, "POST", info);
        System.out.println(orderList);
        // 获取xml结果转换为jsonobject
        Map<String, Object> result = new TreeMap<String, Object>();
        result = WeChatUtils.xml2Map(orderList);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        // 生成调起支付sign
        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
        signParam.put("appid", jsonObject.getString("appid"));
        System.out.println(jsonObject.getString("appid"));
        signParam.put("partnerid", jsonObject.getString("mch_id"));
        signParam.put("prepayid", jsonObject.getString("prepay_id"));
        signParam.put("package", "Sign=WXPay");
        signParam.put("noncestr", nonceStrTemp);
        String a = System.currentTimeMillis() + "";
        signParam.put("timestamp", a.substring(0, 10));
        signParam.put("sign", WeChatUtils.createSign("UTF-8", signParam));
        JSONObject orderString = (JSONObject) JSONObject.toJSON(signParam);
        return orderString;
    }

    public static SortedMap<String, Object> getParametersDirect(String nonceStrTemp, String orgId, Double amount, Double rechargeAmount, Integer recordType, String userAccount) {
//        nonceStrTemp,orgId, amount, rechargeAmount, recordType,userAccount
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters = getParameters();
        String attach=getAttachDirect(orgId, amount, rechargeAmount, recordType, userAccount);
        parameters.put("attach",attach);
        parameters.put("detail","微信支付直充服务");//支付的类容备注
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("notify_url", WeChatH5PayConfig.Direct_url);//通用一个接口（购买和充值）
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = (BigDecimal.valueOf(amount).multiply(new BigDecimal(100))).toBigInteger();
        System.out.println(totalFee);
        parameters.put("total_fee", totalFee);
        parameters.put("sign", WeChatUtils.createH5Sign("UTF-8", parameters));//编码格式
        System.out.println(WeChatUtils.createH5Sign("UTF-8", parameters));
        System.out.println(parameters);
//        parameters.put("device_info", WeChatH5PayConfig.device_info);
        return parameters;
    }

    /**
     * 微信支付——订单格外数据(直充）
     */
    public static String getAttachDirect(String orgId, Double amount, Double rechargeAmount, Integer recordType, String userAccount) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orderId", orgId);
        pbp.put("rechargeAmount", rechargeAmount);
//        pbp.put("userId", userId);
        pbp.put("recordType",String.valueOf(recordType));
        pbp.put("userAccount",userAccount);
        return StringUtil.transMapToStringOther(pbp);
    }


    //封装parameters
    public static SortedMap<String, Object> getParameters(){
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatH5PayConfig.app_id);
        parameters.put("body", WeChatH5PayConfig.body);
        parameters.put("mch_id", WeChatH5PayConfig.mch_id);
//        parameters.put("device_info", WeChatH5PayConfig.device_info);
        parameters.put("sign_type", WeChatH5PayConfig.sign_type);
        parameters.put("spbill_create_ip", WeChatH5PayConfig.spbill_create_ip);
        parameters.put("trade_type", WeChatH5PayConfig.trade_type);
        return parameters;
    }

}
