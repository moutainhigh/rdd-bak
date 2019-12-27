package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/WxSettelRcord")
public class WxSettelRcordController {
    @Autowired
    WxSettelRcordService wxSettelRcordService;

    @GetMapping(value ="/selectSettleRcord")
    public JSONResult selectSettleRcord(WxSettleRcordDTO pageDTO){
        return wxSettelRcordService.getSettleRcord(pageDTO);
    }

    @PostMapping("/settleRecord")
    public JSONResult settleRecord(@RequestBody String recordId){
        return  wxSettelRcordService.settleRecord(recordId);
    }

    @PostMapping("/deleteSettleRecord")
    public JSONResult deleteSettleRecord(@RequestBody String recordId){
        return  wxSettelRcordService.deleteSettleRecord(recordId);
    }

    /**
     * 下载excel表格
     */
//    @PostMapping("/exportRecords")
//    @PermissionCheck(role = "管理员")
//    public JSONResult exportPertrolRecord(HttpServletResponse response, HttpServletRequest request, DeliveryInput deliveryInput)
//    {
//
//    }
}
