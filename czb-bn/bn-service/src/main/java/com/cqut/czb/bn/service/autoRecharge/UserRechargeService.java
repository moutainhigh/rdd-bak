package com.cqut.czb.bn.service.autoRecharge;


import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
public interface UserRechargeService {

    double getBalance(String userId);

    JSONResult getRechargeDetails(String userId, UserRechargeDTO pageDTO);

    JSONResult getTotalRechargeAmount(User user);

    JSONResult insertBatchRecharge(User user, UserRechargeDTO userRechargeDTO);

    boolean drawback(String recordId, boolean dropOrder);

    boolean updatePetrolNum(UserRechargeDTO userRechargeDTO);
}
