package com.cqut.czb.bn.service.PaymentProcess;

import java.util.Map;

public interface WeChatPayBackService {
    Integer addAppletPaymentOrderWeChat(Map<String, Object> restmap);

    Integer addRechargeVipOrderWeChat(Map<String, Object> restmap);

    Integer addRechargeElectricityWechat(Map<String, Object> restmap);
}
