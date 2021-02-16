package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
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
        System.out.println(jsonObject.getString("mweb_url"));
        // 生成调起支付sign
        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
        signParam.put("appid", jsonObject.getString("appid"));
        signParam.put("mweb_url", jsonObject.getString("mweb_url"));
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

    /**
     * 直充服务
     */
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

    //中石化码商和无卡加油使用
    public static SortedMap<String ,Object> getParametersPaymentApplet(String userAccount, Double money, String nonceStrTemp, String orgId,  String stockIds,String userId, WeChatCommodity weChatCommodity){
        SortedMap<String,Object> parameters = new TreeMap<String, Object>();
        parameters=getParameters();
        parameters.put("nonce_str",nonceStrTemp);
        parameters.put("out_trade_no",orgId);
        parameters.put("openid",userAccount);
        BigInteger totalFee = BigDecimal.valueOf(money).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee",totalFee);
        parameters.put("notify_url",WeChatH5PayConfig.Applet_url);
        parameters.put("detail","微信小程序支付");
        String attach = getAttachAppletPayment(orgId,userId,stockIds,money,weChatCommodity.getCommodityId());
        parameters.put("attach",attach);
        parameters.put("sign",WeChatUtils.createRddSign("UTF-8",parameters));
        return parameters;
    }

    /**
     * 微信支付-中石化码商和无卡加油使用
     * @param orgId
     * @param userId
     * @param stockIds
     * @param money
     * @param commodityId
     * @return
     */
    private static String getAttachAppletPayment(String orgId, String userId,String stockIds, Double money, String commodityId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("money",money);
        pbp.put("commodityId",commodityId);
        pbp.put("stockIds",stockIds);
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
