package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;

public interface DirectChargingCommodityMapper {
    int deleteByPrimaryKey(String commodityId);

    int insert(DirectChargingCommodity record);

    int insertSelective(DirectChargingCommodity record);

    DirectChargingCommodity selectByPrimaryKey(String commodityId);

    int updateByPrimaryKeySelective(DirectChargingCommodity record);

    int updateByPrimaryKey(DirectChargingCommodity record);
}