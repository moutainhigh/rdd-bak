package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OilCardRechargeMapperExtra {
    List<DirectChargingOrderDto> getOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);
}
