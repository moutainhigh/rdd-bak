package com.cqut.czb.bn.service.appPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface WeChatAppletPayService {

    JSONObject WeChatAppletBuyCommodity(User user, PayInputDTO payInputDTO);

}
