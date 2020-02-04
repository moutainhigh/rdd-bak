package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.interceptor.PermissionCheck;
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
import java.util.List;

/**
 * 小程序订单管理
 *
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


    /**
     * 小程序订单管理页面数据获取
     * （根据用户角色，筛选获取的订单）
     *
     * @param weChatCommodityOrderDTO
     * @param page
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getTableList")
    public JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(Principal principal, @RequestBody WeChatCommodityOrderDTO weChatCommodityOrderDTO, PageDTO page) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        weChatCommodityOrderDTO.setManagerId(user.getUserId());
        return orderManageService.getTableList(weChatCommodityOrderDTO, page);
    }

    /**
     * 作废订单
     *
     * @param orderId
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/obsoleteOrder")
    public JSONResult<Boolean> obsoleteOrder(@RequestBody String orderId) {
        return orderManageService.obsoleteOrder(orderId);
    }

    /**
     * 通过orderId获取订单详情的数据
     *
     * @param principal
     * @param orderId
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getOrderDetail")
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(Principal principal, String orderId) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        return orderManageService.getOrderDetail(user.getUserId(), orderId);
    }

    /**
     * 获取订单处理数据
     *
     * @param orderId
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getOrderProcessInfo")
    public JSONResult<WeChatCommodityOrderProcess> getOrderProcessInfo(String orderId) {
        return orderManageService.getOrderProcessInfo(orderId);
    }

    /**
     * 处理订单
     *
     * @param principal
     * @param input
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/dealOrder")
    public JSONResult<Boolean> dealOrder(Principal principal, WeChatCommodityOrderProcess input) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        return orderManageService.dealOrder(user.getUserAccount(), input);
    }

    /**
     * 获取销售额
     *
     * @param principal
     * @param weChatCommodityOrderDTO
     * @param page
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getTotalSale")
    public JSONResult<Double> getTotalSale(Principal principal, @RequestBody WeChatCommodityOrderDTO weChatCommodityOrderDTO, PageDTO page) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        weChatCommodityOrderDTO.setManagerId(user.getUserId());
        return orderManageService.getTotalSale(weChatCommodityOrderDTO);
    }
}
