package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.SelectOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface DirectChargingOrderService {
    JSONResult updateRecord(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getRecordByOrderId(String orderId);

    JSONResult dropOrder(String orderId);

    JSONResult dropOrders(String[] orderIds);
}
