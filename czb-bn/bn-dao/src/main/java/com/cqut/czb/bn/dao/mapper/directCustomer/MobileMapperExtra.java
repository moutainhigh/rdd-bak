package com.cqut.czb.bn.dao.mapper.directCustomer;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;


public interface MobileMapperExtra {

    CustomerManageDto getCustomer(DirectCustomersDto directCustomersDto);

    int insertPhoneOrder(DirectChargingOrderDto directChargingOrderDto);

    int insertOilOrder(DirectChargingOrderDto directChargingOrderDto);

}
