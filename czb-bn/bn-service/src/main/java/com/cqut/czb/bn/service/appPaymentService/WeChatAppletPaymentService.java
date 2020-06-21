package com.cqut.czb.bn.service.appPaymentService;

import com.cqut.czb.bn.entity.dto.PayConfig.WeChatBackDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WeChatRechargeVipDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WeChatAppletPaymentService {
    WeChatBackDTO WeChatAppletPaymentBuyCommodity(User user, PayInputDTO payInputDTO);

    WeChatBackDTO WeChatRechargeVip(User user, WeChatRechargeVipDTO rechargeVipDTO);
}
