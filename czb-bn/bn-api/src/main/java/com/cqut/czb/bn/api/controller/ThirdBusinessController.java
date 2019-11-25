package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ThirdBusinessService.GetUnChargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/Rdd")
public class ThirdBusinessController {

    @Autowired
    GetUnChargeOrderService getUnChargeOrderService;

    /**
     * 返回未充值的订单
     */
    @RequestMapping(value="/GetUnChargeOrders", method= RequestMethod.GET)
    public synchronized JSONResult RddGetUnChargeOrders(Principal principal) {
        if(principal==null){
            return new JSONResult(405,"未登录");
        }
//        User user = (User)redisUtils.get(principal.getName());
      return new JSONResult(getUnChargeOrderService.getUnChargeOrder());
    }

    /**
     * 返回未充值的订单
     */
    @RequestMapping(value="/InputChargeOrders", method= RequestMethod.POST)
    public synchronized JSONResult RddInputChargeOrders(Principal principal,String objects) {
        if(principal==null){
            return new JSONResult(405,"未登录");
        }
        return new JSONResult(getUnChargeOrderService.InputChargeOrders(objects));
    }



}
