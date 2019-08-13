package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.VehicleCleanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * app洗车订单管理
 */
@RestController
@RequestMapping("/api/vehicleCleanOrder")
public class vehicleCleanOrderController {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    VehicleCleanOrderService vehicleCleanOrderService;

    /**
     * 获取订单
     * @param principal
     * @return
     */
    @GetMapping("/getVehicleCleanOrder")
    public JSONResult getVehicleCleanOrder(Principal principal){
        if (principal.getName()==null){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
     return new JSONResult(vehicleCleanOrderService.getOrderList(user));
    }

    /**
     * 取消订单
     * @param vehicleCleanOrderDTO
     * @param principal
     * @return
     */
    @PostMapping("/cancelOrder")
    public JSONResult cancelOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO, Principal principal){
        if (principal.getName()==null){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(vehicleCleanOrderService.cancelOrder(vehicleCleanOrderDTO,user));
    }

    /**
     * 完成订单
     * @param vehicleCleanOrderDTO
     * @return
     */
    @PostMapping("/completeOrder")
    public JSONResult completeOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO){
        return new JSONResult(vehicleCleanOrderService.completeOrder(vehicleCleanOrderDTO));
    }
}
