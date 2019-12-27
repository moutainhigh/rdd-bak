package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public JSONResult settleRecord(@RequestBody WxSettleRcordDTO wxSettleRcordDTO){
        return  wxSettelRcordService.settleRecord(wxSettleRcordDTO);
    }

    @PostMapping("/deleteSettleRecord")
    public JSONResult deleteSettleRecord(@RequestBody WxSettleRcordDTO wxSettleRcordDTO){
        return  wxSettelRcordService.deleteSettleRecord(wxSettleRcordDTO);
    }
}
