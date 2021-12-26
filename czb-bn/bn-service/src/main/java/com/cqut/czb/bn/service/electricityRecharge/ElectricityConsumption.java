package com.cqut.czb.bn.service.electricityRecharge;

import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface ElectricityConsumption {
    double getBalance(String userId);

    JSONResult getTotalRechargeAmount(User user);

    JSONResult insertElectricityRecharge(User user, UserRechargeDTO userRechargeDTO);

    JSONResult getCustomers(User user, ElectricityRechargeDto electricityRechargeDto);
}
