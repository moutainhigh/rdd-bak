package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrepaidRefillMapperExtra {
    List<DirectChargingCommodity> getGoodsPrice(@Param("type") Integer type);

}
