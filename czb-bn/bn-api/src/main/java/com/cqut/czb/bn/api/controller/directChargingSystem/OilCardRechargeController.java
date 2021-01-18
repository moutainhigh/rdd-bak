package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/oilCardRecharge")
public class OilCardRechargeController {
    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取当前用户所有订单
     * @param principal
     * @return
     */
    @PostMapping("/getOrderInfoList")
    public JSONResult getInfoNum(Principal principal, Integer type){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(oilCardRechargeService.getOrderInfoList(user.getUserId(), type));
    }

    /**
     * 获取所有订单
     * @return
     */
    @GetMapping ("/getAllOrderInfoList")
    public JSONResult getAllOrderInfoList(Integer type){
        return new JSONResult(oilCardRechargeService.getAllOrderInfoList(type));
    }

    @PostMapping("/bindingOilCard")
    public JSONResult bindingOilCard(Principal principal, OilCardBinging oilCardBinging){
        User user = (User)redisUtils.get(principal.getName());
        return oilCardRechargeService.bindingOilCard(user.getUserId(), oilCardBinging);
    }
}
