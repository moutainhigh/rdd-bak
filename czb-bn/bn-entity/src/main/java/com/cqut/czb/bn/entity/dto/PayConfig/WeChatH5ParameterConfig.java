package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
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
    public static SortedMap<String, Object> getParametersDirect(String nonceStrTemp, String orgId, Double amount, Double rechargeAmount, Integer recordType, String userAccount, Integer isBrowser) {
//        nonceStrTemp,orgId, amount, rechargeAmount, recordType,userAccount
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters = getParameters();
        String attach=getAttachDirect(orgId, amount, rechargeAmount, recordType, userAccount);
        parameters.put("attach",attach);
        if (isBrowser == 0) {
            parameters.put("trade_type", WeChatH5PayConfig.trade_type);
        } else if (isBrowser == 1) {
            parameters.put("trade_type", "JSAPI");
        } else if (isBrowser == 2) {
            parameters.put("trade_type", "APP");
        }
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

    /**
     * 购买积分服务
     */
    public static SortedMap<String, Object> getParametersIntegral(String nonceStrTemp, String orderId, String userId, Double amount, Integer integralAmount, Integer isBrowser) {
//        nonceStrTemp,orgId, amount, rechargeAmount, recordType,userAccount
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters = getParameters();
        String attach=getAttachIntegral(userId, orderId, amount, integralAmount);
        if (isBrowser == 1) {
            parameters.put("appid", WeChatH5PayConfig.app_id);
            parameters.put("trade_type", "JSAPI");
        } else if (isBrowser == 0) {
            parameters.put("appid", WeChatH5PayConfig.app_id);
            parameters.put("trade_type", WeChatH5PayConfig.trade_type);
        } else if (isBrowser == 2) {
            parameters.put("trade_type", "APP");
            parameters.put("appid", "wx1d9987e1abf4c05e");
        }
        System.out.println("9999"+parameters);
        System.out.println(isBrowser);
        parameters.put("attach",attach);
        parameters.put("detail","微信支付积分购买服务");//支付的类容备注
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("notify_url", WeChatH5PayConfig.Integral_url);//通用一个接口（购买和充值）
        parameters.put("out_trade_no", orderId);
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
     * 微信支付——订单格外数据(购买积分）
     */
    public static String getAttachIntegral(String userId, String orderId, Double amount, Integer integralAmount) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("userId", userId);
        pbp.put("orderId", orderId);
        pbp.put("amount", amount);
        pbp.put("integralAmount", integralAmount);
        return StringUtil.transMapToStringOther(pbp);
    }

    //微信库存商品使用
    public static SortedMap<String ,Object> getParametersPaymentApplet(String userAccount, Double money, String nonceStrTemp, String orgId,  String stockIds,String userId, WeChatCommodity weChatCommodity){
        SortedMap<String,Object> parameters = new TreeMap<String, Object>();
        parameters=getParameters();
        parameters.put("appid", WeChatH5PayConfig.app_id);
        parameters.put("trade_type", WeChatH5PayConfig.trade_type);
        parameters.put("nonce_str",nonceStrTemp);
        parameters.put("out_trade_no",orgId);
        parameters.put("openid",userAccount);
        BigInteger totalFee = BigDecimal.valueOf(money).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee",totalFee);
        parameters.put("notify_url",WeChatH5PayConfig.Applet_url);
        parameters.put("detail","微信石化码商支付");
        String attach = getAttachAppletPayment(orgId,userId,stockIds,money,weChatCommodity.getCommodityId());
        parameters.put("attach",attach);
        parameters.put("sign",WeChatUtils.createRddSign("UTF-8",parameters));
        return parameters;

    }

    /**
     * 购买权益商品
     */
    public static SortedMap<String, Object> getParametersEquityGoods(String nonceStrTemp, String orderId, String userId, EquityPaymentDTO equityPaymentDTO) {
//        nonceStrTemp,orgId, amount, rechargeAmount, recordType,userAccount
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters = getParameters();
        String attach=getAttachEquityGoods(userId, orderId, equityPaymentDTO);
        if (equityPaymentDTO.getIsBrowser() == 1) {
            parameters.put("appid", WeChatH5PayConfig.app_id);
            parameters.put("trade_type", "JSAPI");
        } else if (equityPaymentDTO.getIsBrowser() == 0) {
            parameters.put("appid", WeChatH5PayConfig.app_id);
            parameters.put("trade_type", WeChatH5PayConfig.trade_type);
        } else if (equityPaymentDTO.getIsBrowser() == 2) {
            parameters.put("trade_type", "APP");
            parameters.put("appid", "wx1d9987e1abf4c05e");
        }
        System.out.println("9999"+parameters);
        parameters.put("attach",attach);
        parameters.put("detail","微信支付权益商品");//支付的类容备注
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("notify_url", WeChatH5PayConfig.EquityGoods_url);//通用一个接口（购买和充值）
        parameters.put("out_trade_no", orderId);
        BigInteger totalFee = (BigDecimal.valueOf(equityPaymentDTO.getAmount()).multiply(new BigDecimal(100))).toBigInteger();
        System.out.println(totalFee);
        parameters.put("total_fee", totalFee);
        parameters.put("sign", WeChatUtils.createH5Sign("UTF-8", parameters));//编码格式
        System.out.println(WeChatUtils.createH5Sign("UTF-8", parameters));
        System.out.println(parameters);
//        parameters.put("device_info", WeChatH5PayConfig.device_info);
        return parameters;
    }

    /**
     * 微信支付——订单格外数据(购买权益商品）
     */
    public static String getAttachEquityGoods(String userId, String orderId, EquityPaymentDTO equityPaymentDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("userId", userId);
        pbp.put("orderId", orderId);
        pbp.put("amount", equityPaymentDTO.getAmount());
        pbp.put("account",equityPaymentDTO.getAccount());
        pbp.put("productCode",equityPaymentDTO.getProductCode());
        pbp.put("buyNum",equityPaymentDTO.getBuyNum());
        pbp.put("isCallBack",equityPaymentDTO.getIsCallBack());
        pbp.put("tradeType",equityPaymentDTO.getTradeType());
        pbp.put("clientIP",equityPaymentDTO.getClientIP());
        pbp.put("unitPrice",equityPaymentDTO.getUnitPrice());
        pbp.put("totalPrice",equityPaymentDTO.getTotalPrice());
        pbp.put("goodsId",equityPaymentDTO.getGoodsId());
        pbp.put("integralAmount",equityPaymentDTO.getIntegralAmount());
        pbp.put("rechargeType",equityPaymentDTO.getProductCode());
        return StringUtil.transMapToStringOther(pbp);
    }

    //封装parameters
    public static SortedMap<String, Object> getParameters(){
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("body", WeChatH5PayConfig.body);
        parameters.put("mch_id", WeChatH5PayConfig.mch_id);
//        parameters.put("device_info", WeChatH5PayConfig.device_info);
        parameters.put("sign_type", WeChatH5PayConfig.sign_type);
        parameters.put("spbill_create_ip", WeChatH5PayConfig.spbill_create_ip);
        return parameters;
    }

}
