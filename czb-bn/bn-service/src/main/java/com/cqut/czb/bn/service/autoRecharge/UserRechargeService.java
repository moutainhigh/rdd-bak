package com.cqut.czb.bn.service.autoRecharge;


import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface UserRechargeService {
    int insertRecharge(String userId, UserRecharge userRecharge);

    double getBalance(String userId);

    JSONResult getRechargeDetails(String userId, UserRechargeDTO pageDTO);
}
