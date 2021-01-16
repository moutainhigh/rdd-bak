package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingOrder;

public interface DirectChargingOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(DirectChargingOrder record);

    int insertSelective(DirectChargingOrder record);

    DirectChargingOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(DirectChargingOrder record);

    int updateByPrimaryKey(DirectChargingOrder record);
}