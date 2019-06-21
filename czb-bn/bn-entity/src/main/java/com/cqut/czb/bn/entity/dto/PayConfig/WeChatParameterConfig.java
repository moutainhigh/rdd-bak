package com.cqut.czb.bn.entity.dto.PayConfig;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.util.string.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeChatParameterConfig {

    public static SortedMap<String, Object> getParameters(String nonceStrTemp,String orgId, PetrolInputDTO petrolInputDTO, Petrol petrol) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WeChatPayConfig.app_id);
        parameters.put("mch_id", WeChatPayConfig.mch_id);
        parameters.put("device_info", WeChatPayConfig.device_info);
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WeChatPayConfig.sign_type);
        parameters.put("body", WeChatPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = BigDecimal.valueOf(petrolInputDTO.getPetrolPrice()).multiply(new BigDecimal(100))
                .toBigInteger();
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

    //微信支付——订单格外数据
    public static String getAttach(String orgId, String payType,String ownerId,
                                          String petrolNum,String addressId){
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("payType", payType);
        pbp.put("ownerId", ownerId);
        pbp.put("petrolNum", petrolNum);
        pbp.put("addressId", addressId);
        return StringUtil.transMapToStringOther(pbp);
    }

}
