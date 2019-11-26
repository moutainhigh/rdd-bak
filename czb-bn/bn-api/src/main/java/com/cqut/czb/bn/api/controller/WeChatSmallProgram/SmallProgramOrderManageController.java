package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 小程序订单管理Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/api/smallProgramOrderManage")
public class SmallProgramOrderManageController {
    @Autowired
    SmallProgramOrderManageService orderManageService;

    @PostMapping("/getTableList")
    public JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(WeChatCommodityOrderDTO weChatCommodityOrderDTO, PageDTO page) {
        return orderManageService.getTableList(weChatCommodityOrderDTO, page);
    }

    @PostMapping("/obsoleteOrder")
    public JSONResult<Boolean> obsoleteOrder(String orderId) {
        return orderManageService.obsoleteOrder(orderId);
    }

    @PostMapping("/getOrderDetail")
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(String orderId) {
        return orderManageService.getOrderDetail(orderId);
    }
}
