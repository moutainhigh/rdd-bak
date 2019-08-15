package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;
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
     * 获取历史全部订单
     * @param principal
     * @return
     */
    @GetMapping("/getVehicleCleanOrder")
    public JSONResult getVehicleCleanOrder(Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
     return new JSONResult(vehicleCleanOrderService.getOrderList(user));
    }

    @GetMapping("/getServicingOrder")
    public JSONResult getServicingOrder(Principal principal) {
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(vehicleCleanOrderService.getServicingOrder(user));
    }

    /**
     * 取消订单
     * @param vehicleCleanOrderDTO
     * @param principal
     * @return
     */
    @PostMapping("/cancelOrder")
    public JSONResult cancelOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO, Principal principal){
        if (principal ==null || principal.getName()==null ){
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

    /**
     * 评价订单（被评价骑手id不能为空 ）
     * @param riderEvaluate
     * @param principal
     * @return
     */
    @PostMapping("/evaluateOrder")
    public JSONResult evaluateOrder(RiderEvaluate riderEvaluate,Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(vehicleCleanOrderService.evaluateRider(riderEvaluate,user));
    }
}
