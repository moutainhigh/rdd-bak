package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class AliParameterNewConfig {

    /**
     * 转换为支付宝支付实体（油卡相关参数配置）
     * @return
     */
    public static AlipayTradeWapPayModel getBizModelIntegralCoupons(String orgId, IntegralRechargeDTO integralRechargeDTO) {
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody("购买积分");
        model.setSubject("购买积分");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AliPayH5Config.timeout_express);
        model.setTotalAmount(String.valueOf(integralRechargeDTO.getAmount()));
        model.setProductCode(AliPayH5Config.product_wap_code);
        model.setQuitUrl(AliPayH5Config.Return_url);
        model.setSellerId(AliPayH5Config.pay_account);
        model.setPassbackParams(getParamsIntegralCoupons(orgId,integralRechargeDTO));
        return model;
    }

    public static String getParamsIntegralCoupons(String orgId,IntegralRechargeDTO integralRechargeDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", orgId);
        pbp.put("money", integralRechargeDTO.getAmount());
        pbp.put("integralAmount",integralRechargeDTO.getIntegralAmount());
        pbp.put("userId", integralRechargeDTO.getUserId());
        return StringUtil.transMapToStringOther(pbp);
    }
}
