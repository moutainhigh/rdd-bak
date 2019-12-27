package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;

import java.util.List;

public interface WxSettelRcordMapperExtra {
    List<WxSettleRcordDTO> selectSettleRcord(WxSettleRcordDTO pageDTO);

    boolean settleRecord(String recordId);

    boolean deleteSettleRecord(String recordId);

    boolean updateSettleRecord(String recordId);
}
