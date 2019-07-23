package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.util.string.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
    public static SortedMap<String, Object> getParameters(String nonceStrTemp,String orgId, PetrolInputDTO petrolInputDTO, Petrol petrol) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee;
        if (1 == petrolInputDTO.getIsVip()){
            totalFee = BigDecimal.valueOf(petrolInputDTO.getPetrolPrice() * petrol.getDiscount()).multiply(new BigDecimal(100))
                    .toBigInteger();
        }else{
            totalFee = BigDecimal.valueOf(petrolInputDTO.getPetrolPrice()).multiply(new BigDecimal(100))
                    .toBigInteger();
        }

        System.out.println(totalFee);
        parameters.put("total_fee", totalFee);
        parameters.put("spbill_create_ip", WeChatPayConfig.spbill_create_ip);
        parameters.put("notify_url", WeChatPayConfig.notify_url);//通用一个接口（购买和充值）
        parameters.put("trade_type", WeChatPayConfig.trade_type);
        parameters.put("detail","微信支付购买油卡");//支付的类容备注
        String attach=getAttach(
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
    public static String getAttach(String orgId, String payType,String ownerId,String petrolNum,String addressId){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("payType", payType);
        pbp.put("ownerId", ownerId);
        pbp.put("petrolNum", petrolNum);
        pbp.put("addressId", addressId);
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
        String attach=getAttachVip(orgId,userId,vipAreaConfig.getVipPrice());
        parameters.put("attach",attach);
        parameters.put("sign", WeChatUtils.createSign("UTF-8", parameters));//编码格式
        return parameters;
    }

    /**
     * 微信支付——订单格外数据（购买服务）
     */
    public static String getAttachVip(String orgId,String userId,double money){
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
}
