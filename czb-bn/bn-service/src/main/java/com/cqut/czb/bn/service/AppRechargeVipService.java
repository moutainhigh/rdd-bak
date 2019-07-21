package com.cqut.czb.bn.service;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appBuyService.BuyServiceDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface AppRechargeVipService {

    /**
     * 支付宝支付
     * 充值vip
     */
    String AliRechargeVip(User user, RechargeVipDTO rechargeVipDTO);

    /**
     * 微信支付
     * 充值vip
     */
    JSONObject WeChatRechargeVip(User user,RechargeVipDTO rechargeVipDTO);
}
