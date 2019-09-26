package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.service.appPaymentService.AppRechargeVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.UUID;

@Service
public class AppRechargeVipServicelmpl implements AppRechargeVipService {

    @Autowired
    public VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Override
    public String AliRechargeVip(User user, RechargeVipDTO rechargeVipDTO) {
        if(rechargeVipDTO==null){
            System.out.println("地区为空");
            return null;
        }

        //查出相应地区的vip价格
        VipAreaConfig vipAreaConfig=vipAreaConfigMapperExtra.selectVipPrice(rechargeVipDTO.getArea());

        if(vipAreaConfig==null){
            System.out.println("此地区没有VIP");
            return null;
        }

        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("1");//""1"代表的是充值vip
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        Double actualPrice=vipAreaConfig.getVipPrice();
        //购买者id
        String ownerId = user.getUserId();
        request.setBizModel(AliParameterConfig.getBizModelVIP(vipAreaConfig.getVipAreaConfigId(),thirdOrder, actualPrice ,ownerId));//支付订单
        request.setNotifyUrl(AliPayConfig.RechargeVip_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return orderString;
    }

    @Override
    public JSONObject WeChatRechargeVip(User user,RechargeVipDTO rechargeVipDTO) {
        if(rechargeVipDTO==null){
            System.out.println("地区为空");
            return null;
        }
        //查出相应地区的vip价格
        VipAreaConfig vipAreaConfig=vipAreaConfigMapperExtra.selectVipPrice(rechargeVipDTO.getArea());

        if(vipAreaConfig==null){
            System.out.println("此地区没有VIP");
            return null;
        }
        /**
         * 生成起调参数串——返回给app（微信的支付订单）
         */
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String nonceStrTemp = WeChatUtils.getRandomStr();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersVIP(nonceStrTemp,orgId,user.getUserId(),vipAreaConfig);
        return WeChatParameterConfig.getSign( parameters, nonceStrTemp);
    }

}
