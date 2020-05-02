package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class AliParameterConfig {

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeAppPayModel getBizModelPetrolCoupons(String orgId, PetrolInputDTO petrolInputDTO, Petrol petrol) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("爱虎购油");
        model.setSubject("爱虎购油");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(petrol.getPetrolPrice()));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getParamsPetrolCoupons(orgId,petrol,petrolInputDTO));
        return model;
    }

    public static String getParamsPetrolCoupons(String orgId,Petrol petrol,PetrolInputDTO petrolInputDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("payType", petrolInputDTO.getPayType());
        pbp.put("money", petrolInputDTO.getPetrolPrice());
        pbp.put("petrolKind", petrolInputDTO.getPetrolKind());
        pbp.put("ownerId", petrolInputDTO.getOwnerId());
        pbp.put("petrolId", petrol.getPetrolId());
        pbp.put("area",petrolInputDTO.getArea());
        pbp.put("userAccount",petrolInputDTO.getAddressId());
        return StringUtil.transMapToStringOther(pbp);
    }


    //支付宝支付——订单格外数据（油卡相关参数配置）
    public static String getPassBackParams(String area,String orgId, String payType,String contractId,
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
        pbp.put("area",area);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeAppPayModel getBizModel(String area,String orgId, String payType, String contractId,
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
        model.setPassbackParams(getPassBackParams(area,orgId, payType,contractId, money,petrolKind,ownerId,petrolNum,addressId));
        return model;
    }

    //支付宝支付——订单格外数据（购买服务相关参数配置）
    public static String getPassBackParamsBuyService(String thirdOrder,double actualPrice,String commodityId ,String ownerId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("commodityId", commodityId);
        pbp.put("ownerId", ownerId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（购买服务）
     */
    public static AlipayTradeAppPayModel getBizModelBuyService(String thirdOrder,double actualPrice,String commodityId ,String ownerId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("服务代理");
        model.setSubject("服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsBuyService(thirdOrder,actualPrice,commodityId ,ownerId));
        return model;
    }

    /**
     * 转换为支付宝支付实体（充值VIP）
     */
    public static AlipayTradeAppPayModel getBizModelVIP(String vipAreaConfigId,String thirdOrder,double actualPrice,String ownerId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("vip充值");
        model.setSubject("vip充值");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsVIP(vipAreaConfigId,thirdOrder,actualPrice ,ownerId));
        return model;
    }

    //支付宝支付——订单格外数据（充值VIP相关参数配置）
    public static String getPassBackParamsVIP(String vipAreaConfigId,String thirdOrder,double actualPrice,String ownerId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("ownerId", ownerId);
        pbp.put("vipAreaConfigId", vipAreaConfigId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（购买洗车服务）
     */
    public static AlipayTradeAppPayModel getBizModelBuyCarWash(String couponId,String thirdOrder,double actualPrice,String serviceId ,String ownerId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("洗车服务代理");
        model.setSubject("洗车服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsBuyCarWash(couponId,thirdOrder,actualPrice,serviceId ,ownerId));
        return model;
    }


    //支付宝支付——订单格外数据（购买洗车服务）
    public static String getPassBackParamsBuyCarWash(String couponId,String thirdOrder,double actualPrice,String serverId ,String ownerId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("serverId", serverId);
        pbp.put("ownerId", ownerId);
        pbp.put("couponId",couponId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（点餐）
     */
    public static AlipayTradeAppPayModel getBizModelBuyDish(String thirdOrder,double actualPrice ,String ownerId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("点餐服务代理");
        model.setSubject("点餐服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsBuyDish(thirdOrder,actualPrice ,ownerId));
        return model;
    }
    //支付宝支付——订单格外数据（点餐）
    public static String getPassBackParamsBuyDish(String thirdOrder,double actualPrice ,String ownerId) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("ownerId", ownerId);
        return StringUtil.transMapToStringOther(pbp);
    }
}
