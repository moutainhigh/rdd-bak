package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/WxSettelRcord")
public class WxSettelRcordController {
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
}
