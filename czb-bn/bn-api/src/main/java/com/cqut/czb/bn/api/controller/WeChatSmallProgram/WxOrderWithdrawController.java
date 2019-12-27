package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxOrderWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/WxOrderWithdraw")
public class WxOrderWithdrawController {

    @Autowired
    WxOrderWithdrawService wxOrderWithdrawService;

    /**
     * 微信小程序提现
     */
    @GetMapping(value ="/getAllOrder")
    public JSONResult getAllOrder(WxOrderWithdrawDTO wxOrderWithdrawDTO){
        return wxOrderWithdrawService.toGetAllOrder(wxOrderWithdrawDTO);
    }

    @PostMapping("/toWithDraw")
    public JSONResult toWithDraw(@RequestBody WxOrderWithdrawDTO wxOrderWithdrawDTO){
        return wxOrderWithdrawService.toWithDraw(wxOrderWithdrawDTO);
    }
}
