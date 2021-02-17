package com.cqut.czb.bn.service.paymentNew.paybackService;

import java.util.Map;

public interface PayBackService {
    Map AliPayback(Object[] param, String consumptionType);

    Map<String, Integer> WeChatPayBack(Object[] param, String consumptionType);
}
