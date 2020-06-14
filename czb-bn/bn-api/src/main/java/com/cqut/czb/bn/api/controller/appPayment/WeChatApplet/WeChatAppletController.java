package com.cqut.czb.bn.api.controller.appPayment.WeChatApplet;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatPayConfig;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appPaymentService.AppBuyServiceService;
import com.cqut.czb.bn.service.appPaymentService.AppRechargeVipService;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPayService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/WeChatAppletVip")
public class WeChatAppletController {

    @Autowired
    public AppBuyServiceService appBuyServiceService;

    @Autowired
    public RedisUtils redisUtils;

    @Autowired
    public AppRechargeVipService appRechargeVipService;

    @Autowired
    WeChatAppletPayService weChatAppletPayService;

    @RequestMapping(value = "/WeChatApplet", method = RequestMethod.POST)
    public  JSONResult WeChatApplet(Principal principal,@RequestBody PayInputDTO payInputDTO) {
        User user = (User)redisUtils.get(principal.getName());
        if(user==null){
            return new JSONResult("未登录",405,null);
        }
        return new JSONResult(weChatAppletPayService.WeChatAppletBuyCommodity(user,payInputDTO));
    }
}
