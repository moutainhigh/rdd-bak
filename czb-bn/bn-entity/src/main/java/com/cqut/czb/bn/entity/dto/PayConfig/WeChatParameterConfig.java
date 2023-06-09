package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WeChatRechargeVipDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatStock;
import com.cqut.czb.bn.util.string.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class WeChatParameterConfig {

    //统一获取签名,微信支付签名请求
    public static JSONObject getSign(SortedMap<String, Object> parameters, String nonceStrTemp){
        // 转为xml格式
        String info = WeChatUtils.map2xml(parameters);
        String orderList = null;//用于保存起调参数,
        orderList = WeChatHttpUtil.httpsRequest(WeChatPayConfig.URL, "POST", info);
        // 获取xml结果转换为jsonobject
        Map<String, Object> result = new TreeMap<String, Object>();
        result = WeChatUtils.xml2Map(orderList);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        // 生成调起支付sign
        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
        signParam.put("appid", jsonObject.getString("appid"));
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
     * 购买油卡（微信）
     */
    public static SortedMap<String, Object> getParameters(String nonceStrTemp,String orgId, double money, PetrolInputDTO petrolInputDTO, Petrol petrol) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee;
        totalFee = BigDecimal.valueOf(money).multiply(new BigDecimal(100)).toBigInteger();
        System.out.println(totalFee);
        parameters.put("total_fee", totalFee);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("notify_url", WeChatPayConfig.notify_url);//通用一个接口（购买和充值）
        parameters.put("trade_type", WeChatPayConfig.trade_type);
        parameters.put("detail","微信支付购买油卡");//支付的类容备注
        String attach=getAttach(petrolInputDTO.getArea(),
                orgId,petrolInputDTO.getPayType(),
                petrolInputDTO.getOwnerId(),
                petrol.getPetrolNum(),petrolInputDTO.getAddressId());

        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
        return parameters;
    }

    /**
     * 购买油卡，微信支付——订单格外数据（微信）
     */
    public static String getAttach(String area,String orgId, String payType,String ownerId,String petrolNum,String addressId){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("payType", payType);
        pbp.put("ownerId", ownerId);
        pbp.put("petrolNum", petrolNum);
        pbp.put("addressId", addressId);
        pbp.put("area", area);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 购买服务
     */
    public static SortedMap<String, Object> getParametersService(String nonceStrTemp, String orgId, String userId, CommodityDTO commodityDTO) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = BigDecimal.valueOf(commodityDTO.getCommodityPrice()).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee", totalFee);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("notify_url", WeChatPayConfig.BuyService_url);//通用一个接口（购买和充值）
        parameters.put("trade_type", WeChatPayConfig.trade_type);
        parameters.put("detail","微信支付购买服务");//支付的类容备注
        String attach=getAttach2(orgId,userId,commodityDTO);
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
        return parameters;
    }

    /**
     * 微信支付——订单格外数据（购买服务）
     */
    public static String getAttach2(String orgId,String userId,CommodityDTO commodityDTO){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("commodityId",commodityDTO.getCommodityId());
        pbp.put("money",commodityDTO.getCommodityPrice());
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 充值vip
     */
    public static SortedMap<String, Object> getParametersVIP(String nonceStrTemp, String orgId, String userId,VipAreaConfig vipAreaConfig) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters=getParameters();
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = BigDecimal.valueOf(vipAreaConfig.getVipPrice()).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee", totalFee);
        parameters.put("notify_url", WeChatPayConfig.RechargeVip_url);//通用一个接口（购买和充值）
        parameters.put("detail","微信支付vip服务");//支付的类容备注
        String attach=getAttachVip(vipAreaConfig.getVipAreaConfigId(),orgId,userId,vipAreaConfig.getVipPrice());
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
        return parameters;
    }

    public static SortedMap<String, Object> getParametersDirect(String nonceStrTemp, String orgId, Double amount, Double rechargeAmount, Integer recordType, String userAccount) {
//        nonceStrTemp,orgId, amount, rechargeAmount, recordType,userAccount
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters=getParameters();
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("out_trade_no", orgId);
        Double totalFee = amount;
        parameters.put("total_fee", totalFee);
        parameters.put("notify_url", WeChatPayConfig.Direct_url);//通用一个接口（购买和充值）
        parameters.put("detail","微信支付直充服务");//支付的类容备注
        String attach=getAttachDirect(orgId, amount, rechargeAmount, recordType, userAccount);
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
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

    //微信小程序用
    public static SortedMap<String, Object> getParametersApplet(String userAccount,Double money,String nonceStrTemp, String orgId, String userId, WeChatCommodity weChatCommodity) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters=getParametersApplet();
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("out_trade_no", orgId);
        parameters.put("openid",userAccount);
        BigInteger totalFee = BigDecimal.valueOf(money).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee", totalFee);
        parameters.put("notify_url", WeChatPayConfig.applet_url);//通用一个接口（购买和充值）
        parameters.put("detail","微信小程序支付");//支付的类容备注
        String attach=getAttachApplet(orgId,userId,money,weChatCommodity.getCommodityId());
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createRddSign("UTF-8", parameters));//编码格式
        return parameters;
    }
    //微信小程序库存商品使用
    public static SortedMap<String ,Object> getParametersPaymentApplet(String userAccount, Double money, String nonceStrTemp, String orgId,  String stockIds,String userId, WeChatCommodity weChatCommodity){
       SortedMap<String,Object> parameters = new TreeMap<String, Object>();
       parameters=getParametersApplet();
       parameters.put("nonce_str",nonceStrTemp);
       parameters.put("out_trade_no",orgId);
       parameters.put("openid",userAccount);
       BigInteger totalFee = BigDecimal.valueOf(money).multiply(new BigDecimal(100)).toBigInteger();
       parameters.put("total_fee",totalFee);
       parameters.put("notify_url",WeChatPayConfig.applet_url2);
       parameters.put("detail","微信小程序支付");
       String attach = getAttachAppletPayment(orgId,userId,stockIds,money,weChatCommodity.getCommodityId());
       parameters.put("attach",attach);
       parameters.put("sign",WeChatUtils.createRddSign("UTF-8",parameters));
       return parameters;

    }
    public static SortedMap<String, Object> getParametersRechargeVip(String userAccount, Dict dict, String nonceStrTemp, String orgId, WeChatRechargeVipDTO weChatRechargeVipDTO) {
        SortedMap<String,Object> parameters = new TreeMap<String, Object>();
        parameters = getParametersApplet();
        parameters.put("nonce_str",nonceStrTemp);
        parameters.put("out_trade_no",orgId);
        parameters.put("openid",userAccount);
        BigInteger totalFee = BigDecimal.valueOf(weChatRechargeVipDTO.getVipPrice()).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee",totalFee);
        parameters.put("notify_url",WeChatPayConfig.applet_url3);
        parameters.put("detail","微信小程序支付");
        String attach = getAttachRechargeVip(orgId,weChatRechargeVipDTO.getVipPrice(),dict.getDictId(),weChatRechargeVipDTO);
        parameters.put("attach",attach);
        parameters.put("sign",WeChatUtils.createRddSign("UTF-8",parameters));
        return parameters;
    }



    /**
     * 微信支付-库存商品数据
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
     * 微信支付充值vip
     */
    private static String getAttachRechargeVip(String orgId, Double vipPrice, String dictId, WeChatRechargeVipDTO weChatRechargeVipDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", weChatRechargeVipDTO.getUserId());
        pbp.put("money",vipPrice);
        pbp.put("vipAreaConfigId",dictId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 微信支付——订单格外数据(充值vip）
     */
    public static String getAttachVip(String vipAreaConfigId ,String orgId,String userId,double money){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("money",money);
        pbp.put("vipAreaConfigId",vipAreaConfigId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 微信支付——微信小程序购买商品
     */
    public static String getAttachApplet(String orgId,String userId,double money,String commodityId){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("money",money);
        pbp.put("commodityId",commodityId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 微信支付——订单格外数据(直充系统）
     */
    public static String getAttachDirect(String vipAreaConfigId ,String orgId,String userId,double money){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("money",money);
        pbp.put("vipAreaConfigId",vipAreaConfigId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 购买洗车服务
     */
    public static SortedMap<String, Object> getParametersBuyCarWash(String couponId,Double couponMoney,String nonceStrTemp, String orgId, String userId,double money,String serviceId) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = (BigDecimal.valueOf(money).subtract(BigDecimal.valueOf(couponMoney))).multiply(new BigDecimal(100)).toBigInteger();
        parameters.put("total_fee", totalFee);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("notify_url", WeChatPayConfig.BuyCarWash_url);//通用一个接口（购买和充值）
        parameters.put("trade_type", WeChatPayConfig.trade_type);
        parameters.put("detail","微信支付购买服务");//支付的类容备注
        String attach=getAttachBuyCarWash(couponId,orgId,userId,serviceId,money);
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
        return parameters;
    }

    public static String getAttachBuyCarWash(String couponId,String orgId,String userId,String serverId,double money ){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("serverId",serverId);
        pbp.put("money",money);
        pbp.put("couponId",couponId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     *  点餐
     */
    public static SortedMap<String, Object> getParametersBuyDish(String nonceStrTemp, String orgId, String userId,double money) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = (BigDecimal.valueOf(money).multiply(new BigDecimal(100))).toBigInteger();
        parameters.put("total_fee", totalFee);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("notify_url", WeChatPayConfig.BuyDish_url);//点餐
        parameters.put("trade_type", WeChatPayConfig.trade_type);
        parameters.put("detail","微信支付点餐");//支付的类容备注
        String attach=getAttachBuyDish(orgId,userId,money);
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
        return parameters;
    }

    public static String getAttachBuyDish(String orgId,String userId,double money ){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("ownerId", userId);
        pbp.put("money",money);
        return StringUtil.transMapToStringOther(pbp);
    }

    //封装parameters
    public static SortedMap<String, Object> getParameters(){
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("trade_type", WeChatPayConfig.trade_type);
        return parameters;
    }

    //封装parameters——微信小程序用
    public static SortedMap<String, Object> getParametersApplet(){
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.sapp_id);
        parameters.put("mch_id", WeChatPayConfig.smch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("trade_type", WeChatPayConfig.WeChat_applet_trade_type);
        return parameters;
    }


}
