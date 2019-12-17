package com.cqut.czb.bn.api.controller.appPayment;


import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.CleanServerVehicleDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appPaymentService.AppBuyCarWashService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/AppBuyCarWash")
public class AppBuyCarWashController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppBuyCarWashService appBuyCarWashService;

    @RequestMapping(value = "/AliBuyCarWash" ,method = RequestMethod.POST)
    public JSONResult AliBuyCarWash(Principal principal,@RequestBody CleanServerVehicleDTO cleanServerVehicleDTO){

        User user = (User)redisUtils.get(principal.getName());
        String info =appBuyCarWashService.AliBuyCarWash(user,cleanServerVehicleDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }

    @RequestMapping(value = "/WeChatBuyCarWash" ,method = RequestMethod.POST)
    public JSONResult WeChatBuyCarWash(Principal principal, @RequestBody CleanServerVehicleDTO cleanServerVehicleDTO){

        User user = (User)redisUtils.get(principal.getName());
        JSONObject info =appBuyCarWashService.WeChatBuyCarWash(user,cleanServerVehicleDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }

}
