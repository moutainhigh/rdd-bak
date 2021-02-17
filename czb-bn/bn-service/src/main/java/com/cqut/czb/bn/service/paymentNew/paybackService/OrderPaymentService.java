package com.cqut.czb.bn.service.paymentNew.paybackService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface OrderPaymentService {

    //支付宝回调测试接口
    String AliOrderPaymentNotify(HttpServletRequest request, String consumptionType);

    //解析订单
    Map<String, String> parseOrder(Map<String, String> params, Map requestParams);

    String WeChatOrderPaymentNotify(HttpServletRequest request, String consumptionType);
}
