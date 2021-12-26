package com.cqut.czb.bn.dao.mapper.directCustomer;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerBalanceDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;


public interface MobileMapperExtra {

    CustomerManageDto getCustomer(DirectCustomersDto directCustomersDto);

    int insertPhoneOrder(DirectChargingOrderDto directChargingOrderDto);

    int insertOilOrder(DirectChargingOrderDto directChargingOrderDto);

    int updateConsumption(CustomerManageDto customerManageDto);

    Integer getOrderState(DirectChargingOrderDto directChargingOrderDto);

    Integer getState(DirectChargingOrderDto directChargingOrderDto);

    CustomerBalanceDto getTheBalance(DirectCustomersDto directCustomersDto);

}
