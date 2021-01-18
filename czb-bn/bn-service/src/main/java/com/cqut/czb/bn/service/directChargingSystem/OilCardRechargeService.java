package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OilCardRechargeService{
    List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type);
}
