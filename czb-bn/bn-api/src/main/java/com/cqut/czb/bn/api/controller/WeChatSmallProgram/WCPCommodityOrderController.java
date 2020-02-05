package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityComdirmOrderDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WCPCommodityOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取商家所有小程序订单
     * @param principal
     * @param orderState 0 未确认 1 已确认
     * @return
     */
    @GetMapping("/getAllCommodityOrderByLeader")
    public JSONResult getAllCommodityOrderByLeader(Principal principal,Integer orderState,Integer page){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.getAllCommodityOrderByLeader(user.getUserId(), orderState,page));
    }

    /**
     * 获取商家所有订单金额
     * @param principal
     * @return
     */
    @GetMapping("/getTotalPrice")
    public JSONResult getTotalPrice(Principal principal,Integer orderState){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        double x = wcpCommodityOrderService.getTotalPrice(user.getUserId(), orderState);
        return new JSONResult(wcpCommodityOrderService.getTotalPrice(user.getUserId(), orderState));
    }
    /**
     * 确认收货(小程序寄送订单)
     * @param principal
     * @param orderId
     * @return
     */
    @GetMapping("/comfirmDeliveryState")
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

    @GetMapping("/getOneCommodityOrderByShop")
    public JSONResult getOneCommodityOrderByShop(Principal principal,String orderId){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.getOneCommodityOrderByShop(user.getUserId(), orderId));
    }
    /**
     * 商家在微信小程序上确认订单
     * @param principal
     * @param weChatCommodityComdirmOrderDTO
     * @return
     */
    @PostMapping("/comfirmWCPCommodityOrder")
    public JSONResult comfirmWCPCommodityOrder(Principal principal, @RequestBody WeChatCommodityComdirmOrderDTO weChatCommodityComdirmOrderDTO){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpCommodityOrderService.comfirmCommodityOrder(user.getUserId(), weChatCommodityComdirmOrderDTO));
    }

}