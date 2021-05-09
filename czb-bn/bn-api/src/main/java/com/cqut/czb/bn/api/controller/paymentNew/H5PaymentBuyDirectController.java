package com.cqut.czb.bn.api.controller.paymentNew;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.AliPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/DirectChargingPay")
public class H5PaymentBuyDirectController {
    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * app buy service and input information(购买服务和录入信息)支付宝
     */
    @RequestMapping(value = "/AlipayRechargeDirect", method = RequestMethod.POST)
    public JSONResult AlipayRechargeDirect(Principal principal, @RequestBody DirectChargingOrderDto directChargingOrderDto) {
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
        String info =oilCardRechargeService.AlipayRechargeDirect(directChargingOrderDto);
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
        JSONObject info =oilCardRechargeService.WeChatRechargeDirect(user, directChargingOrderDto);
        if(info==null){
            return new JSONResult("充值失败", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("充值成功",200,info);
        }
    }


}
