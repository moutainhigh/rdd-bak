package com.cqut.czb.bn.api.controller.food;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.food.DishOrder;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.MyOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/foodOrder")
@RestController
public class MyOrderController {

    @Autowired
    MyOrderService myOrderService;
    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getAllMyOrderList")
    public JSONResult getAllMyOrderList(Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(myOrderService.getAllMyOrderList(user));
    }

    @GetMapping("/getOrderDetails")
    public JSONResult getOrderDetails(DishOrderDTO dishOrder){
        return new JSONResult(myOrderService.getOrderDetails(dishOrder));
    }

    @PostMapping("/cancelDishOrder")
    public JSONResult cancelDishOrder(DishOrderDTO dishOrderDTO) {
        return new JSONResult(myOrderService.cancelDishOrder(dishOrderDTO));
    }

    @PostMapping("/confirmOrder")
    public JSONResult confirmOrder(Principal principal, @Param("orderTime") String orderTime, @Param("orderId")String orderId) {
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(myOrderService.confirmOrder(user,orderTime,orderId));
    }
}