package com.cqut.czb.bn.service.impl.paymentNewServiceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatH5ParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyIntegralService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedMap;
import java.util.UUID;

/**
 * @author wangya
 */
@Service("H5PaymentBuyIntegralService")
public class H5PaymentBuyIntegralServiceImpl implements H5PaymentBuyIntegralService {

    @Autowired
    IntegralPurchaseMapperExtra integralPurchaseMapperExtra;

    @Override
    public String AliBuyIntegral(User user, IntegralRechargeDTO integralRechargeDTO) {

        //判空
//        if(user==null && integralRechargeDTO==null){
//            System.out.println("用户信息不全");
//            return null;
//        }

        Double couponMoney=0.0;
        //生成起吊参数
        //用于保存起调参数,
        String orderString = null;
        //"0"为购买积分
        AlipayNewClientConfig alipayClientConfig = AlipayNewClientConfig.getInstance("0");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        double actualPrice= BigDecimal.valueOf(integralRechargeDTO.getAmount()).subtract(BigDecimal.valueOf(couponMoney)).doubleValue();

        //购买者id
//        String ownerId = user.getUserId();
        //        String userId = user.getUserId();
//        String ownerId = "703614235874580972";
        String ownerId = user.getUserId();
        integralRechargeDTO.setUserId(ownerId);
        System.out.println("积分userId" + ownerId);
        //支付订单
        request.setBizModel(AliParameterNewConfig.getBizModelIntegralCoupons(thirdOrder,integralRechargeDTO));
        request.setReturnUrl(AliPayH5Config.Return_url);
        //支付回调接口
        request.setNotifyUrl(AliPayH5Config.IntegralRecharge_url);
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);;
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //payMethod 1为支付宝，2为微信
        Boolean orderInsert = insertBuyIntegral(thirdOrder,ownerId,integralRechargeDTO,1);
        System.out.println(orderString);
        return orderString;
    }

    /**
     * 微信
     * @param user
     * @param integralRechargeDTO
     * @return
     */
    @Override
    public com.alibaba.fastjson.JSONObject WeChatBuyIntegral(User user, IntegralRechargeDTO integralRechargeDTO) {

        /**
         * 生成起调参数串（微信的支付订单）
         */
        //订单标识
        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");

        String nonceStrTemp = WeChatUtils.getRandomStr();

        //支付的金额
        Double amount = integralRechargeDTO.getAmount();

        Integer integralAmount = integralRechargeDTO.getIntegralAmount();

        // userId
//        String userId = user.getUserId();
        String userId = "703610893704287052";

        Integer isBrowser = integralRechargeDTO.getIsBrowser();


        // 设置参数
        SortedMap<String, Object> parameters = WeChatH5ParameterConfig.getParametersIntegral(nonceStrTemp, orderId, userId, amount, integralAmount, isBrowser);
        Boolean insertOrder = insertBuyIntegral(orderId, userId, integralRechargeDTO,2);
        return WeChatH5ParameterConfig.getSign(parameters, nonceStrTemp);
    }

    /**
     * 插入购买记录
     * @param integralRechargeDTO
     * @param orderId
     * @param userId
     * @return
     */
    public Boolean insertBuyIntegral(String orderId, String userId, IntegralRechargeDTO integralRechargeDTO,int rechargeWay) {
        IntegralPurchaseRecord integralPurchaseRecord = new IntegralPurchaseRecord();
        integralPurchaseRecord.setIntegralPurchaseRecordId(orderId);
        integralPurchaseRecord.setUserId(userId);
        integralPurchaseRecord.setAmount(integralRechargeDTO.getAmount());
        integralPurchaseRecord.setIntegralAmount(integralRechargeDTO.getIntegralAmount());
        //支付状态：0未支付，1已支付
        integralPurchaseRecord.setIsReceived(0);
        integralPurchaseRecord.setRechargeWay(rechargeWay);
        integralPurchaseRecord.setThirdTradeNum(orderId);
        Boolean insertRecords = integralPurchaseMapperExtra.insertIntegralPurchaseRecord(integralPurchaseRecord) > 0;
        return insertRecords;
    }
}
