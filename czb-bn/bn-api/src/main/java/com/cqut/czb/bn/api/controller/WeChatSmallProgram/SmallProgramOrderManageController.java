package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.DealCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 小程序订单管理
 * @author 袁才明
 */
@CrossOrigin
@RestController
@RequestMapping("/api/smallProgramOrderManage")
public class SmallProgramOrderManageController {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    SmallProgramOrderManageService orderManageService;

    @PostMapping("/getTableList")
    public JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(@RequestBody WeChatCommodityOrderDTO weChatCommodityOrderDTO, PageDTO page) {
        return orderManageService.getTableList(weChatCommodityOrderDTO, page);
    }

    @PostMapping("/obsoleteOrder")
    public JSONResult<Boolean> obsoleteOrder(@RequestBody String orderId) {
        return orderManageService.obsoleteOrder(orderId);
    }

    @PostMapping("/getOrderDetail")
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(Principal principal, String orderId) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        return orderManageService.getOrderDetail(user.getUserId(), orderId);
    }

    @PostMapping("/getOrderProcessInfo")
    public JSONResult<WeChatCommodityOrderProcess> getOrderProcessInfo(@RequestBody String orderId) {
        return orderManageService.getOrderProcessInfo(orderId);
    }

    @PostMapping("/dealOrder")
    public JSONResult<Boolean> dealOrder(Principal principal, WeChatCommodityOrderProcess input) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        return orderManageService.dealOrder(user.getUserAccount(), input);
    }

}
