package com.cqut.czb.bn.dao.mapper.electricityRecharge;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityTotalDto;
import com.cqut.czb.bn.entity.dto.integral.CommodityDetailsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ElectricityRechargeMapperExtra {
    List<ElectricityRechargeDto> getCustomers(ElectricityRechargeDto electricityRechargeDto);

    int editOrder(ElectricityRechargeDto electricityRechargeDto);

    Double getRechargeAmount(@Param("commodityId")String commodityId);

    Double getMaxIntegralAmount(@Param("commodityId")String commodityId);

    int insertOrder(ElectricityRechargeDto electricityRechargeDto);

    String getRegional(@Param("orderId")String orgId);

    int changeState(@Param("orderId")String orgId);

    int changeFinishTime(ElectricityRechargeDto electricityRechargeDto);

    CustomerManageDto getCustomer(ElectricityDto electricityDto);

    int insertThirdOrder(ElectricityDto electricityDto);
}
