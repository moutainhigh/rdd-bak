package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;

import java.util.List;

public interface ServerCouponMapperExtra {
    List<ServerCoupon> selectByPrimaryKey(String ownerId);
}
