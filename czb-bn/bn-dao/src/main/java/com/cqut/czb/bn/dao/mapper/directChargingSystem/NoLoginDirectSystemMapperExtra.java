package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.CustomerPhoneOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface NoLoginDirectSystemMapperExtra {
    String getCommodityId(DirectChargingOrderDto directChargingOrderDto);

    int insertPhoneOrder(CustomerPhoneOrderDto customerPhoneOrderDto);

    String findCustomerId(DirectChargingOrderDto directChargingOrderDto);

    CustomerPhoneOrderDto getOrderDetails(CustomerPhoneOrderDto customerPhoneOrderDto);

    int updateCustomerState(CustomerPhoneOrderDto customerPhoneOrderDto);
}
