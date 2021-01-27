package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.util.string.StringUtil;
import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.Map;

public class AliParameterConfig {

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeAppPayModel getBizModelPetrolCoupons(String orgId, PetrolInputDTO petrolInputDTO, Petrol petrol) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("中石化码商");
        model.setSubject("中石化码商");
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
        pbp.put("money", petrolInputDTO.getPetrolPrice());
        pbp.put("petrolKind", petrolInputDTO.getPetrolKind());
        pbp.put("ownerId", petrolInputDTO.getOwnerId());
        pbp.put("petrolNum", petrol.getPetrolNum());
        pbp.put("area",petrolInputDTO.getArea());
        pbp.put("userAccount",petrolInputDTO.getUserAccount());
        return StringUtil.transMapToStringOther(pbp);
    }


    //支付宝支付——订单格外数据（油卡相关参数配置）
    public static String getPassBackParams(String area, String orgId, String payType, String contractId,
                                           Double money, Integer petrolKind , String ownerId,
                                           String petrolNum, String addressId, Integer isSpecial) {
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
        pbp.put("isSpecial",String.valueOf(isSpecial));
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeAppPayModel getBizModel(String area,String orgId, String payType, String contractId,
                                                           double money, Integer petrolKind , String ownerId,
                                                           String petrolNum, String addressId,Integer isSpecial) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        if(isSpecial == 1){
            model.setBody("RDD购油");
            model.setSubject("RDD购油");
        }else {
            model.setBody("爱虎购油");
            model.setSubject("爱虎购油");
        }
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(money));
//        model.setTotalAmount("0.01");
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParams(area,orgId, payType,contractId, money,petrolKind,ownerId,petrolNum,addressId,isSpecial));
        return model;
    }

    //支付宝支付——订单格外数据（购买服务相关参数配置）
    public static String getPassBackParamsBuyService(String thirdOrder,double actualPrice,String commodityId ,String ownerId,Integer isSpecial) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("commodityId", commodityId);
        pbp.put("ownerId", ownerId);
        pbp.put("isSpecial",isSpecial);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（购买服务）
     */
    public static AlipayTradeAppPayModel getBizModelBuyService(String thirdOrder,double actualPrice,String commodityId ,String ownerId,Integer isSpecial) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("服务代理");
        model.setSubject("服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsBuyService(thirdOrder,actualPrice,commodityId ,ownerId,isSpecial));
        return model;
    }

    /**
     * 转换为支付宝支付实体（充值VIP）
     */
    public static AlipayTradeAppPayModel getBizModelVIP(String vipAreaConfigId,String thirdOrder,double actualPrice,String ownerId,Integer isSpecial) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("vip充值");
        model.setSubject("vip充值");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsVIP(vipAreaConfigId,thirdOrder,actualPrice ,ownerId,isSpecial));
        return model;
    }

    //支付宝支付——订单格外数据（充值VIP相关参数配置）
    public static String getPassBackParamsVIP(String vipAreaConfigId,String thirdOrder,double actualPrice,String ownerId,Integer isSpecial) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("ownerId", ownerId);
        pbp.put("isSpecial", String.valueOf(isSpecial));
        pbp.put("vipAreaConfigId", vipAreaConfigId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（购买洗车服务）
     */
    public static AlipayTradeAppPayModel getBizModelBuyCarWash(String couponId,String thirdOrder,double actualPrice,String serviceId ,String ownerId,Integer isSpecial) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("洗车服务代理");
        model.setSubject("洗车服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsBuyCarWash(couponId,thirdOrder,actualPrice,serviceId ,ownerId,isSpecial));
        return model;
    }


    //支付宝支付——订单格外数据（购买洗车服务）
    public static String getPassBackParamsBuyCarWash(String couponId,String thirdOrder,double actualPrice,String serverId ,String ownerId,Integer isSpecial) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("serverId", serverId);
        pbp.put("ownerId", ownerId);
        pbp.put("isSpecial",String.valueOf(isSpecial));
        pbp.put("couponId",couponId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（点餐）
     */
    public static AlipayTradeAppPayModel getBizModelBuyDish(String thirdOrder,double actualPrice ,String ownerId,Integer isSpecial) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("点餐服务代理");
        model.setSubject("点餐服务代理");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getPassBackParamsBuyDish(thirdOrder,actualPrice ,ownerId,isSpecial));
        return model;
    }
    //支付宝支付——订单格外数据（点餐）
    public static String getPassBackParamsBuyDish(String thirdOrder,double actualPrice ,String ownerId,Integer isSpecial) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("money", actualPrice);
        pbp.put("ownerId", ownerId);
        pbp.put("isSpecial",String.valueOf(isSpecial));
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体（直充相关参数配置）
     * @return
     */
    public static AlipayTradeWapPayModel getPhonePill(String orderId,double amount, double rechargeAmount, String userId, Integer recordType, String userAccount, String cardNum) {
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody("RDD话费直充");
        model.setSubject("RDD话费直充");
        model.setOutTradeNo(orderId);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(amount));
        model.setProductCode(AliPayConfig.product_wap_code);
        model.setQuitUrl("http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Web_port+"/recharge");
        model.setSellerId("2088731798282131");
        System.out.println(userAccount);
        model.setPassbackParams(getPhonePillParams(orderId, rechargeAmount , userId, recordType,userAccount,cardNum));
        return model;
    }

    //支付宝支付—— 直充值相关参数配置
    public static String getPhonePillParams(String orderId, double rechargeAmount, String userId, Integer recordType, String cardNum,String userAccount) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orderId", orderId);
        pbp.put("rechargeAmount", rechargeAmount);
        pbp.put("userId", userId);
        pbp.put("recordType",String.valueOf(recordType));
        pbp.put("userAccount",userAccount);
        if (recordType != 1){
            pbp.put("cardNum",cardNum);
        }
        return StringUtil.transMapToStringOther(pbp);
    }
}
