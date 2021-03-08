package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;

import java.util.List;

public interface DirectChargingOrderMapperExtra {
    List<DirectChargingOrderDto> getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto);

    List<DirectChargingOrderDto> getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto);

    double getTotalRechargeAmount();

    double getUserTotalRechargeAmount(String userId);
}
