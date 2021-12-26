package com.cqut.czb.bn.api.controller.paymentNew;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/ElectricityPay")
public class H5PaymentBuyElectricityController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ElectricityRechargeService electricityRechargeService;

    /**
     * app buy service and input information(购买服务和录入信息)支付宝
     */
    @RequestMapping(value = "/AlipayRechargeDirect", method = RequestMethod.POST)
    public JSONResult AlipayRechargeDirect(Principal principal, @RequestBody DirectChargingOrderDto directChargingOrderDto) {
        System.out.println("进入水电费支付宝充值");
//        if (directChargingOrderDto.getIsNeedLogin() == 1) {
        User user = (User)redisUtils.get(principal.getName());
        directChargingOrderDto.setUserId(user.getUserId());
//        }
//        User user = (User)redisUtils.get(principal.getName());
//        directChargingOrderDto.setUserId(user.getUserId());
        if (directChargingOrderDto.getIsDirectPartner() != 1 && directChargingOrderDto.getRecordType() != 1) {
            directChargingOrderDto.setCardholder(user.getUserAccount());
            directChargingOrderDto.setUserAccount(user.getUserAccount());
        }
        directChargingOrderDto.setIsNew(1);
//        return new JSONResult("暂未开通支付宝支付",ResponseCodeConstants.FAILURE);
        String info =electricityRechargeService.AlipayRechargeDirect(directChargingOrderDto);
        if(info==null){
            return new JSONResult("充值失败", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("充值成功",200,info);
        }
    }

    /**
     * app buy service and input information(购买服务和录入信息)微信
     */
    @RequestMapping(value = "/WeChatRechargeDirect", method = RequestMethod.POST)
    public JSONResult WeChatRechargeDirect(Principal principal,@RequestBody DirectChargingOrderDto directChargingOrderDto) {
//        return new JSONResult("暂不支持微信支付",ResponseCodeConstants.FAILURE);
//        User user = new User();
//        User user=new User();
//        user.setUserId("155892403286206");
//        rechargeVipDTO.setArea("重庆市");
        User user = (User)redisUtils.get(principal.getName());
        directChargingOrderDto.setUserId(user.getUserId());
        if (directChargingOrderDto.getIsDirectPartner() != 1 && directChargingOrderDto.getRecordType() != 1) {
            directChargingOrderDto.setCardholder(user.getUserAccount());
            directChargingOrderDto.setUserAccount(user.getUserAccount());
        }
        System.out.println(directChargingOrderDto);
        JSONObject info =electricityRechargeService.WeChatRechargeDirect(directChargingOrderDto);
        if(info==null){
            return new JSONResult("充值失败", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("充值成功",200,info);
        }
    }
}
