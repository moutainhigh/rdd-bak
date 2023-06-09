package com.cqut.czb.bn.service.appPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyService.BuyServiceDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;

import java.util.Map;

public interface AppBuyServiceService {

    /**
     * 用支付宝购买服务
     * 生成起调参数串——返回给app（支付订单）
     */
    String AliPayBuyService(User user,BuyServiceDTO buyServiceDTO);

    /**
     * 用微信购买油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    JSONObject WeChatBuyService(User user,BuyServiceDTO buyServiceDTO);

}
