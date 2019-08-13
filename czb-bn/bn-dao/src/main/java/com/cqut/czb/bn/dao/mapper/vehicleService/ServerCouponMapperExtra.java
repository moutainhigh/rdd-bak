package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;

import java.util.List;

public interface ServerCouponMapperExtra {
    List<ServerCouponDTO> selectByPrimaryKey(String ownerId);
}
