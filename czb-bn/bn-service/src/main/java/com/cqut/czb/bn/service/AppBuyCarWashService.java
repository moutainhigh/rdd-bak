package com.cqut.czb.bn.service;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.CleanServerVehicleDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface AppBuyCarWashService {

    /**
     * 用支付宝购买服务
     * 生成起调参数串——返回给app（支付订单）
     */
    String AliBuyCarWash(User user, CleanServerVehicleDTO cleanServerVehicleDTO);

    /**
     * 用微信购买油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    JSONObject WeChatBuyCarWash(User user,  CleanServerVehicleDTO cleanServerVehicleDTO);

}
