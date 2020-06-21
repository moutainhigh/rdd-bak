package com.cqut.czb.bn.api.controller.appPayment.WeChatApplet;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WeChatRechargeVipDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPayService;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/WeChatAppletPaymentVip")
public class WechatAppletPaymentController {

    @Autowired
    public RedisUtils redisUtils;
    @Autowired
    WeChatAppletPaymentService weChatAppletPaymentService;


    /**
     * 微信小程序商品支付
     * @param   principal,payInputDTO
     * @return
     */
    @RequestMapping(value = "/WeChatAppletPayment", method = RequestMethod.POST)
    public JSONResult WeChatAppletPayment(Principal principal, @RequestBody PayInputDTO payInputDTO) {
        User user = (User)redisUtils.get(principal.getName());
        if(user==null){
            return new JSONResult("未登录",405,null);
        }
        return new JSONResult( weChatAppletPaymentService.WeChatAppletPaymentBuyCommodity(user,payInputDTO));
    }

    /**
     * 微信会员支付
     * @param principal
     * @param rechargeVipDTO
     * @return
     */
    @RequestMapping(value = "/WeChatRechargeVip", method = RequestMethod.POST)
    public JSONResult WeChatRechargeVip(Principal principal, @RequestBody WeChatRechargeVipDTO rechargeVipDTO){
        User user = (User)redisUtils.get(principal.getName());
        if(user==null){
            return new JSONResult("未登录",405,null);
        }
        return new JSONResult(weChatAppletPaymentService.WeChatRechargeVip(user,rechargeVipDTO));
    }
}
