package com.cqut.czb.bn.dao.mapper.electricityRecharge;

import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElectricityConsumptionMapperExtra {
    double getBalance(String userId);

    double getTotalRechargeAmount(String userId);

    int insertConsumption(ElectricityRechargeDto electricityRechargeDto);

    int changeBalance(ElectricityRechargeDto electricityRechargeDto);

    List<ElectricityRechargeDto> getCustomers(ElectricityRechargeDto electricityRechargeDto);
}
