package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WCPCommodityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-26 16:01
 */
@RestController
@RequestMapping("/api/WCPCommodityOrder")
public class WCPCommodityOrderController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    WCPCommodityOrderService wcpCommodityOrderService;

    /**
     * 获取用户小程序最新的订单
     * @param principal
     * @return
     */
    @GetMapping("/getCurrentOrder")
    public JSONResult getCurrentOrder(Principal principal){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.getCurrentOrder(user.getUserId()));
    }

    /**
     * 获取用户所有小程序订单
     * @param principal
     * @return
     */
    @GetMapping("/getAllCommodityOrder")
    public JSONResult getAllCommodityOrder(Principal principal){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.getAllCommodityOrder(user.getUserId()));
    }

    /**
     * 确认收货(小程序寄送订单)
     * @param principal
     * @param orderId
     * @return
     */
    @PostMapping("/comfirmDeliveryState")
    public JSONResult comfirmDeliveryState(Principal principal, String orderId){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.comfirmDeliveryState(user.getUserId(), orderId));
    }

    /**
     * 获取用户的单个订单
     * @param principal
     * @param orderId
     * @return
     */
    @GetMapping("/getOneCommodityOrder")
    public JSONResult getOneCommodityOrderById(Principal principal, String orderId){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.getOneCommodityOrderById(user.getUserId(), orderId));
    }
}