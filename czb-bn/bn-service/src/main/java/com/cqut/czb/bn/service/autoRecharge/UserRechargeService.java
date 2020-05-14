package com.cqut.czb.bn.service.autoRecharge;


import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;

public interface UserRechargeService {
    int insertRecharge(String userId, UserRecharge userRecharge);

    double getBalance(String userId);
}
