package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.github.pagehelper.PageInfo;

public interface DirectChargingPartnerService {
    PageInfo getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto);

    PageInfo getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto);
}
