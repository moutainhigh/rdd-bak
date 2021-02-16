package com.cqut.czb.bn.service.impl.paymentNewServiceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyIntegralService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author wangya
 */
@Service("H5PaymentBuyIntegralService")
public class H5PaymentBuyIntegralServiceImpl implements H5PaymentBuyIntegralService {

    @Override
    public String AliBuyIntegral(User user, IntegralRechargeDTO integralRechargeDTO) {

        //判空
        if(user==null && integralRechargeDTO==null){
            System.out.println("用户信息不全");
            return null;
        }

        Double couponMoney=0.0;
        //生成起吊参数
        //用于保存起调参数,
        String orderString = null;
        //"0"为购买积分
        AlipayNewClientConfig alipayClientConfig = AlipayNewClientConfig.getInstance("0");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        double actualPrice= BigDecimal.valueOf(integralRechargeDTO.getIntegralAmount()).subtract(BigDecimal.valueOf(couponMoney)).doubleValue();

        //购买者id
        String ownerId = user.getUserId();
        //支付订单
        request.setBizModel(AliParameterNewConfig.getBizModelIntegralCoupons(ownerId,integralRechargeDTO));
        //支付回调接口
        request.setNotifyUrl(AliPayConfig.IntegralRecharge_url);
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //payMethod 1为支付宝，2为微信
        InsertIntegralPurchaseRecord(thirdOrder,actualPrice,integralRechargeDTO,1);

        return orderString;
    }

    private void InsertIntegralPurchaseRecord(String thirdOrder, double actualPrice, IntegralRechargeDTO integralRechargeDTO, int i) {

    }
}
