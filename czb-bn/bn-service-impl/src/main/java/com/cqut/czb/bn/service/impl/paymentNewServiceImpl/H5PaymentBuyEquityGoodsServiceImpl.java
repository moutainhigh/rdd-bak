package com.cqut.czb.bn.service.impl.paymentNewServiceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyEquityGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.UUID;

@Service
public class H5PaymentBuyEquityGoodsServiceImpl implements H5PaymentBuyEquityGoodsService {

    @Autowired
    IntegralPurchaseMapperExtra integralPurchaseMapperExtra;

    /**
     * 微信
     * @param user
     * @param equityPaymentDTO
     * @return
     */
    @Override
    public com.alibaba.fastjson.JSONObject WeChatBuyEquityGoods(User user, EquityPaymentDTO equityPaymentDTO) {

        /**
         * 生成起调参数串（微信的支付订单）
         */
        //订单标识
        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");

        String nonceStrTemp = WeChatUtils.getRandomStr();

        // userId
        String userId = user.getUserId();
//        String userId = "703610893704287052";

        // 设置参数
        SortedMap<String, Object> parameters = WeChatH5ParameterConfig.getParametersEquityGoods(nonceStrTemp, orderId, userId, equityPaymentDTO);
        Boolean insertOrder = insertBuyEquityGoods(orderId, userId, equityPaymentDTO,2);
        return WeChatH5ParameterConfig.getSign(parameters, nonceStrTemp);
    }

    @Override
    public String AliBuyEquityGoods(User user, EquityPaymentDTO equityPaymentDTO) {
        //判空
        if(user==null && equityPaymentDTO==null){
            System.out.println("用户信息不全");
            return null;
        }

        Double couponMoney=0.0;
        //生成起吊参数
        //用于保存起调参数,
        String orderString = null;
        //"0"为购买积分
        AlipayNewClientConfig alipayClientConfig = AlipayNewClientConfig.getInstance("2");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        double actualPrice= BigDecimal.valueOf(equityPaymentDTO.getAmount()).subtract(BigDecimal.valueOf(couponMoney)).doubleValue();

        //购买者id
//        String ownerId = user.getUserId();
        //        String userId = user.getUserId();
//        String ownerId = "703614235874580972";
        String userId = user.getUserId();
        System.out.println("积分userId" + userId);
        //支付订单
        request.setBizModel(AliParameterNewConfig.getBizModelEquityGoodsCoupons(thirdOrder,actualPrice,userId,equityPaymentDTO));
        request.setReturnUrl(AliPayH5Config.EquityReturn_url);
        //支付回调接口
        request.setNotifyUrl(AliPayH5Config.EquityRecharge_url);
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);;
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //payMethod 1为支付宝，2为微信
        Boolean insertOrder = insertBuyEquityGoods(thirdOrder, userId, equityPaymentDTO,1);
        System.out.println(orderString);
        return orderString;
    }

    /**
     * 插入购买记录
     * @param equityPaymentDTO
     * @param orderId
     * @param userId
     * @return
     */
    public Boolean insertBuyEquityGoods(String orderId, String userId, EquityPaymentDTO equityPaymentDTO,int rechargeWay) {
        EquityPaymentDTO equityPaymentDTO1 = new EquityPaymentDTO();
        equityPaymentDTO1.setOrderId(orderId);
        equityPaymentDTO1.setUserId(userId);
        equityPaymentDTO1.setAccount(equityPaymentDTO.getAccount());
        equityPaymentDTO1.setProductCode(equityPaymentDTO.getProductCode());
        equityPaymentDTO1.setBuyNum(equityPaymentDTO.getBuyNum());
        equityPaymentDTO1.setClientIP(equityPaymentDTO.getClientIP());
        equityPaymentDTO1.setUnitPrice(equityPaymentDTO.getUnitPrice());
        equityPaymentDTO1.setGoodsId(equityPaymentDTO.getGoodsId());
        equityPaymentDTO1.setPayPrice(String.valueOf(equityPaymentDTO.getAmount()));
        equityPaymentDTO1.setPayMethod(rechargeWay);
        Boolean insertRecords = integralPurchaseMapperExtra.insertEquityGoodsOrder(equityPaymentDTO1) > 0;
        return insertRecords;
    }
}
