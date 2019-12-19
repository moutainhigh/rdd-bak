package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatTOWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WCPWithdrawService {

    JSONResult selectWithdrawInfo(WeChatWithdrawDTO pageDTO);

    JSONResult toWithdraw(WeChatTOWithdrawDTO weChatTOWithdrawDTO);
}
