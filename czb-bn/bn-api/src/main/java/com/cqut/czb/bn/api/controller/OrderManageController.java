package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.order.OrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orderManage")
public class OrderManageController {

    @Autowired
    OrderManageService orderManageService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getOrderList")
    public JSONResult getOrderList(OrderDTO orderDTO, PageDTO pageDTO){
        return new JSONResult(orderManageService.getOrderList(orderDTO,pageDTO));
    }

    @PostMapping("/updateOrder")
    public JSONResult updateOrder( OrderDTO orderDTO){
        return new JSONResult(orderManageService.updateOrderState(orderDTO));
    }
    @GetMapping("/getOrderByShop")
    public JSONResult getOrderByShop(OrderDTO orderDTO, PageDTO pageDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user==null||"".equals(user.getUserId())){
            return new JSONResult();
        }
        return new JSONResult(orderManageService.getOrderByShop(orderDTO,pageDTO,user));
    }

    @GetMapping("/getOrderServiceConsumption")
    public JSONResult getOrderServiceConsumption(OrderDTO orderDTO,PageDTO pageDTO,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user==null||"".equals(user.getUserId())){
            return new JSONResult();
        }
        return new JSONResult(orderManageService.getConsumptionOfService(orderDTO,pageDTO,user));
    }
}
