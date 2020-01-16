package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxOrderWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/WxOrderWithdraw")
public class WxOrderWithdrawController {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    WxOrderWithdrawService wxOrderWithdrawService;

    /**
     * 微信小程序商家未结算订单获取
     */
    @PermissionCheck(role = "管理员")
    @GetMapping(value ="/getAllOrder")
    public JSONResult getAllOrder(WxOrderWithdrawDTO wxOrderWithdrawDTO){
        return wxOrderWithdrawService.toGetAllOrder(wxOrderWithdrawDTO);
    }

    /**
     *微信小程序商家订单结算
     */
    @PermissionCheck(role = "管理员")
    @GetMapping("/toWithDraw")
    public JSONResult toWithDraw(Principal principal,WxOrderWithdrawDTO wxOrderWithdrawDTO){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return wxOrderWithdrawService.toWithDraw(user.getUserId(),wxOrderWithdrawDTO);
    }
}
