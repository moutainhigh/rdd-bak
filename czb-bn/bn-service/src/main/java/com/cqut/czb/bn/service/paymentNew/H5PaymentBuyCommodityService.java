package com.cqut.czb.bn.service.paymentNew;

import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatBackDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface H5PaymentBuyCommodityService {
    com.alibaba.fastjson.JSONObject WeChatAppletPaymentBuyCommodity(User user, H5StockDTO h5StockDTO);

    String AliAppletPaymentBuyCommodity(User user, PayInputDTO payInputDTO);
}
