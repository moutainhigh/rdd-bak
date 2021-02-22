package com.cqut.czb.bn.service.impl.paymentNewServiceImpl;

import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatH5ParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyEquityGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        equityPaymentDTO1.setAccount(equityPaymentDTO.getAccount());
        equityPaymentDTO1.setProductCode(equityPaymentDTO.getProductCode());
        equityPaymentDTO1.setBuyNum(equityPaymentDTO.getBuyNum());
        equityPaymentDTO1.setClientIP(equityPaymentDTO.getClientIP());
        equityPaymentDTO1.setUnitPrice(equityPaymentDTO.getUnitPrice());
        equityPaymentDTO1.setGoodsId(equityPaymentDTO.getGoodsId());
        Boolean insertRecords = integralPurchaseMapperExtra.insertEquityGoodsOrder(equityPaymentDTO1) > 0;
        return insertRecords;
    }
}
