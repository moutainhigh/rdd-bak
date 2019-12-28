package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface WxSettelRcordService {
    JSONResult getSettleRcord(WxSettleRcordDTO pageDTO);

    JSONResult settleRecord(WxSettleRcordDTO wxSettleRcordDTO);

    JSONResult deleteSettleRecord(WxSettleRcordDTO wxSettleRcordDTO);
}
