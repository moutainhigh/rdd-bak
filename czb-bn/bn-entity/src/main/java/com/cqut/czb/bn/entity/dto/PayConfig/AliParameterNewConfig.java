package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class AliParameterNewConfig {

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeAppPayModel getBizModelIntegralCoupons(String orgId, IntegralRechargeDTO integralRechargeDTO) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("购买积分");
        model.setSubject("购买积分");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AliPayConfig.timeout_express);
        model.setTotalAmount(String.valueOf(integralRechargeDTO.getIntegralAmount()));
        model.setProductCode(AliPayConfig.product_code);
        model.setPassbackParams(getParamsIntegralCoupons(orgId,integralRechargeDTO));
        return model;
    }

    public static String getParamsIntegralCoupons(String orgId,IntegralRechargeDTO integralRechargeDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("money", integralRechargeDTO.getIntegralAmount());
        pbp.put("ownerId", integralRechargeDTO.getOrderId());
        pbp.put("userAccount",integralRechargeDTO.getUserId());
        return StringUtil.transMapToStringOther(pbp);
    }
}
