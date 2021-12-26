package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.CustomerPhoneOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface NoLoginDirectSystemService {
    String getCommodityId(DirectChargingOrderDto directChargingOrderDto);

    String insertPhoneOrder(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getOrderDetails(CustomerPhoneOrderDto customerPhoneOrderDto);
}
