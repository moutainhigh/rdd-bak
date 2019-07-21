package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class AliParameterConfig {
    //支付宝支付——订单格外数据（油卡相关参数配置）
    public static String getPassBackParams(String orgId, String payType,String contractId,
                                    Double money, Integer petrolKind ,String ownerId,
                                    String petrolNum,String addressId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("payType", payType);
        pbp.put("money", money);
        pbp.put("petrolKind", petrolKind);
        pbp.put("ownerId", ownerId);
        pbp.put("petrolNum", petrolNum);
        pbp.put("addressId", addressId);
        pbp.put("contractId",contractId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeAppPayModel getBizModel(String orgId, String payType, String contractId,
                                                           double money, Integer petrolKind , String ownerId,
                                                           String petrolNum, String addressId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("爱虎购油");
        model.setSubject("爱虎购油");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(money));
//        model.setTotalAmount("0.01");
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParams(orgId, payType,contractId, money,petrolKind,ownerId,petrolNum,addressId));
        return model;
    }

    //支付宝支付——订单格外数据（油卡相关参数配置）
    public static String getPassBackParams2(String thirdOrder,double actualPrice,String commodityId ,String ownerId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("commodityId", commodityId);
        pbp.put("ownerId", ownerId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（购买服务相关参数配置）
     */
    public static AlipayTradeAppPayModel getBizModel2(String thirdOrder,double actualPrice,String commodityId ,String ownerId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("服务代理");
        model.setSubject("服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
//        model.setTotalAmount("0.01");
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParams2(thirdOrder,actualPrice,commodityId ,ownerId));
        return model;
    }

    /**
     * 转换为支付宝支付实体（充值VIP相关参数配置）
     */
    public static AlipayTradeAppPayModel getBizModelVIP(String thirdOrder,double actualPrice,String ownerId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("vip充值");
        model.setSubject("vip充值");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
//        model.setTotalAmount("0.01");
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsVIP(thirdOrder,actualPrice ,ownerId));
        return model;
    }

    //支付宝支付——订单格外数据（充值VIP相关参数配置）
    public static String getPassBackParamsVIP(String thirdOrder,double actualPrice,String ownerId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("ownerId", ownerId);
        return StringUtil.transMapToStringOther(pbp);
    }


}
