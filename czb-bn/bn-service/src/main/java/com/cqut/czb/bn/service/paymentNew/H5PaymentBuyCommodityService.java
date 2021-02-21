package com.cqut.czb.bn.service.paymentNew;

import com.cqut.czb.bn.entity.dto.PayConfig.WeChatBackDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface H5PaymentBuyCommodityService {
    WeChatBackDTO WeChatAppletPaymentBuyCommodity(User user, PayInputDTO payInputDTO);

    String AliAppletPaymentBuyCommodity(User user, PayInputDTO payInputDTO);
}
