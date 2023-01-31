package com.cqut.czb.bn.service.autoRecharge;


import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.RechargeOrder;
import com.cqut.czb.bn.entity.dto.directChargingSystem.ThirdOrderCallback;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserRechargeService {

    double getBalance(String userId);

    JSONResult getRechargeDetails(String userId, UserRechargeDTO pageDTO);

    JSONResult getTotalRechargeAmount(User user);

    JSONResult insertBatchRecharge(User user, UserRechargeDTO userRechargeDTO);

    ThirdOrderCallback insertThirdOil(RechargeOrder order);

    boolean drawbackWithPet(String recordId, boolean dropOrder);

    boolean drawback(String recordId);

    ThirdOrderCallback insertThirdPhone(RechargeOrder order);

    boolean updatePetrolNum(UserRechargeDTO userRechargeDTO);

    JSONResult insertCommonOrder(User user, RechargeOrder order, int type);

    JSONResult getCommonUserOrder(DirectChargingOrderDto directChargingOrderDto);
}
