package com.cqut.czb.bn.api.controller.appPayment;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appPaymentService.AppBuyServiceService;
import com.cqut.czb.bn.service.appPaymentService.AppRechargeVipService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/AppRechargeVip")
public class AppRechargeVipController {

    @Autowired
    public AppBuyServiceService appBuyServiceService;

    @Autowired
    public RedisUtils redisUtils;

    @Autowired
    public AppRechargeVipService appRechargeVipService;

    /**
     * app buy service and input information(购买服务和录入信息)支付宝
     */
    @RequestMapping(value = "/AliRechargeVip", method = RequestMethod.POST)
        public JSONResult AliRechargeVip(Principal principal,@RequestBody RechargeVipDTO rechargeVipDTO) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("155892403286206");
//        rechargeVipDTO.setArea("重庆市");
        String info =appRechargeVipService.AliRechargeVip(user,rechargeVipDTO);
        if(info==null){
            return new JSONResult("充值失败", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("充值成功",200,info);
        }
    }

    /**
     * app buy service and input information(购买服务和录入信息)微信
     */
    @RequestMapping(value = "/WeChatRechargeVip", method = RequestMethod.POST)
    public JSONResult WeChatRechargeVip(Principal principal,@RequestBody RechargeVipDTO rechargeVipDTO) {
        return new JSONResult("暂不支持微信支付",ResponseCodeConstants.FAILURE);
        //        User user = (User)redisUtils.get(principal.getName());
////        User user=new User();
////        user.setUserId("155892403286206");
////        rechargeVipDTO.setArea("重庆市");
//        JSONObject info =appRechargeVipService.WeChatRechargeVip(user,rechargeVipDTO);
//        if(info==null){
//            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
//        }else {
//            return  new JSONResult("购买成功",200,info);
//        }
    }
}
