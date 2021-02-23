package com.cqut.czb.bn.service.paymentNew;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface H5PaymentBuyEquityGoodsService {

    JSONObject WeChatBuyEquityGoods(User user, EquityPaymentDTO equityPaymentDTO);

    String AliBuyEquityGoods(User user, EquityPaymentDTO equityPaymentDTO);
}
