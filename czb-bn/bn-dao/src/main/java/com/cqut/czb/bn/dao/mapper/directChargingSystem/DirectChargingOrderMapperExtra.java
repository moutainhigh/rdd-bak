package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.User;

import java.util.List;

public interface DirectChargingOrderMapperExtra {
    List<DirectChargingOrderDto> getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto);

    List<DirectChargingOrderDto> getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto);

    double getTotalRechargeAmount();

    double getUserTotalRechargeAmount(DirectChargingOrderDto directChargingOrderDto);

    int updateRecordByOrderId(DirectChargingOrderDto directChargingOrderDto);

    DirectChargingOrderDto getRecordByOrderId(String orderId);

    int deleteDirectChargingPartnerOrder(User user);
}
