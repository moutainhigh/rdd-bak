package com.cqut.czb.bn.service.paymentNew;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.User;

import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface H5PaymentBuyIntegralService {
    JSONObject WeChatBuyIntegral(User user, IntegralRechargeDTO integralRechargeDTO);


    String AliBuyIntegral(User user, IntegralRechargeDTO integralRechargeDTO);
}
