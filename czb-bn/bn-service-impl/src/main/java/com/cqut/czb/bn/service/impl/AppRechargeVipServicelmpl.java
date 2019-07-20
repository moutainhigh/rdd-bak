package com.cqut.czb.bn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.appBuyService.BuyServiceDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.service.AppRechargeVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class AppRechargeVipServicelmpl implements AppRechargeVipService {

    @Autowired
    public VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Override
    public String AliRechargeVip(User user, RechargeVipDTO rechargeVipDTO) {
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
        request.setBizModel(AliParameterConfig.getBizModelVIP(thirdOrder, actualPrice ,ownerId));//支付订单
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
        //查出相应地区的vip价格
        VipAreaConfig vipAreaConfig=vipAreaConfigMapperExtra.selectVipPrice(rechargeVipDTO.getArea());

        if(vipAreaConfig==null){
            System.out.println("此地区没有VIP");
            return null;
        }

//        rechargeVipDTO.setUserId();

//        String orgId = WeChatUtils.getRandomStr();
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String nonceStrTemp = WeChatUtils.getRandomStr();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParameters2(nonceStrTemp,orgId,user.getUserId(),null);
        // 转为xml格式
        String info = WeChatUtils.map2xml(parameters);
        String orderList = null;//用于保存起调参数,
        orderList = WeChatHttpUtil.httpsRequest(WeChatPayConfig.URL, "POST", info);
        // 获取xml结果转换为jsonobject
        Map<String, Object> result = new TreeMap<String, Object>();
        result = WeChatUtils.xml2Map(orderList);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        // 生成调起支付sign
        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
        signParam.put("appid", jsonObject.getString("appid"));
        signParam.put("partnerid", jsonObject.getString("mch_id"));
        signParam.put("prepayid", jsonObject.getString("prepay_id"));
        signParam.put("package", "Sign=WXPay");
        signParam.put("noncestr", nonceStrTemp);
        String a = System.currentTimeMillis() + "";
        signParam.put("timestamp", a.substring(0, 10));
        signParam.put("sign", WeChatUtils.createSign("UTF-8", signParam));
        JSONObject orderString = (JSONObject) JSONObject.toJSON(signParam);

        return orderString;
    }
}
