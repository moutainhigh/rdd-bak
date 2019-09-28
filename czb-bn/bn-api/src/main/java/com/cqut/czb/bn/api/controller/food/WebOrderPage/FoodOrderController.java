package com.cqut.czb.bn.api.controller.food.WebOrderPage;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodOrder;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.WebOrderService.WebOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/foodOrder")
public class FoodOrderController {
    @Autowired
    WebOrderService service;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/search")
    public JSONResult search(FoodOrder foodOrder, PageDTO pageDTO, Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return service.search(foodOrder, pageDTO, user);
    }

    @GetMapping("/sureOrder")
    public JSONResult sureOrder(@Param("orderId") String orderId) {
        return service.sureOrder(orderId);
    }

    @GetMapping("/cancelOrder")
    public JSONResult cancelOrder(@Param("orderId") String orderId) {
        return service.cancelOrder(orderId);
    }

}
