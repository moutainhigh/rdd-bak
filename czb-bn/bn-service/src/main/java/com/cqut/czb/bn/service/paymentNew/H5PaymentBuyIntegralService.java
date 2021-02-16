package com.cqut.czb.bn.service.paymentNew;

import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface H5PaymentBuyIntegralService {

    String AliBuyIntegral(User user, IntegralRechargeDTO integralRechargeDTO);
}
