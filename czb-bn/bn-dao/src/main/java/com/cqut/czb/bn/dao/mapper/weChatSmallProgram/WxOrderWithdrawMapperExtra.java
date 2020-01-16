package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.GetWxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatSettleRecord;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxOrderWithdrawMapperExtra {

    List<WxOrderWithdrawDTO> toGetAllOrder(WxOrderWithdrawDTO wxOrderWithdrawDTO);

    List<WxOrderWithdrawDTO> selectWithDraw(WxOrderWithdrawDTO wxOrderWithdrawDTO);

    int insertWithdraw(WeChatSettleRecord weChatSettleRecord);

    Double getTotalAmount(@Param("shopId") String shopId);

    int toBangding(@Param("orderId") List orderId, @Param("settledRecordId") String settled_record_id);
}
