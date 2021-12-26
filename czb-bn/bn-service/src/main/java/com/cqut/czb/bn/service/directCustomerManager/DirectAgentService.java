package com.cqut.czb.bn.service.directCustomerManager;

import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface DirectAgentService {
    double getBalance(String userId);

    JSONResult insertRecharge(User user, UserRechargeDTO userRechargeDTO);

    JSONResult getRechargeDetails(String userId, DirectCustomerManagerDto pageDTO);

    JSONResult getTotalRechargeAmount(String userId);
}
