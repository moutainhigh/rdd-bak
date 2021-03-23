package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.AlipayObject;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
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

    public static AlipayTradeWapPayModel getBizModelICommodityCoupons(double money, String thirdOrder,String userId, H5StockDTO h5StockDTO) {
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody("库存商品");
        model.setSubject("库存商品");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayH5Config.timeout_express);
        model.setTotalAmount(String.valueOf(money));
        model.setProductCode(AliPayH5Config.product_wap_code);
        model.setQuitUrl(AliPayH5Config.CommodityReturn_url);
        model.setSellerId(AliPayH5Config.pay_account);
        model.setPassbackParams(getParamsCommodityCoupons(thirdOrder,userId,money,h5StockDTO));
        return model;
    }

    private static String getParamsCommodityCoupons(String thirdOrder, String userId, double money,H5StockDTO h5StockDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("orgId", thirdOrder);
        pbp.put("ownerId", userId);
        pbp.put("money", money);
        pbp.put("stockId", h5StockDTO.getStockId());
        pbp.put("integralAmount",h5StockDTO.getIntegralAmount());
        return StringUtil.transMapToStringOther(pbp);
    }

    public static AlipayTradeWapPayModel getBizModelEquityGoodsCoupons(String thirdOrder, double actualPrice, String userId, EquityPaymentDTO equityPaymentDTO) {
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody("权益商品");
        model.setSubject("权益商品");
        model.setOutTradeNo(thirdOrder);
        model.setTimeoutExpress(AliPayH5Config.timeout_express);
        model.setTotalAmount(String.valueOf(actualPrice));
        model.setProductCode(AliPayH5Config.product_wap_code);
        model.setQuitUrl(AliPayH5Config.EquityReturn_url);
        model.setSellerId(AliPayH5Config.pay_account);
        model.setPassbackParams(getParamsEquityGoodsCoupons(thirdOrder,userId,equityPaymentDTO));
        return model;
    }

    private static String getParamsEquityGoodsCoupons(String thirdOrder, String userId, EquityPaymentDTO equityPaymentDTO) {
        Map<String, Object> pbp = new HashMap<>();
        pbp.put("userId", userId);
        pbp.put("orderId", thirdOrder);
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
}
