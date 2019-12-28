package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WxOrderWithdrawService {

    JSONResult toGetAllOrder(WxOrderWithdrawDTO wxOrderWithdrawDTO);

    JSONResult toWithDraw(WxOrderWithdrawDTO wxOrderWithdrawDTO);
}
