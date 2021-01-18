package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OilCardRechargeService{
    List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type);

    List<DirectChargingOrderDto> getAllOrderInfoList(Integer type);

    JSONResult bindingOilCard(String userId, OilCardBinging oilCardBinging);
}
