package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.GetWxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxOrderWithdrawMapperExtra {

    List<WxOrderWithdrawDTO> toGetAllOrder(GetWxOrderWithdrawDTO getWxOrderWithdrawDTO);

    List<WxOrderWithdrawDTO> selectWithDraw(WxOrderWithdrawDTO wxOrderWithdrawDTO);
}
