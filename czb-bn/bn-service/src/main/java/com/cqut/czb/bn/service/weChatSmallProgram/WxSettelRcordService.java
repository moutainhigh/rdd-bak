package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface WxSettelRcordService {
    JSONResult getSettleRcord(WxSettleRcordDTO pageDTO);

    JSONResult settleRecord(String recordId);

    JSONResult deleteSettleRecord(String recordId);
}
