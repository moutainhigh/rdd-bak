package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class AliParameterConfig {
    //支付宝支付——订单格外数据
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
     * 转换为支付宝支付实体
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
}
