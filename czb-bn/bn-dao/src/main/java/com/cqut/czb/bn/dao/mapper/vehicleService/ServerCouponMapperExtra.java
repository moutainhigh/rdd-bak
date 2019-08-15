package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServerCouponMapperExtra {
    List<ServerCouponDTO> selectByPrimaryKey(ServerCouponDTO serverCouponDTO);

    int updateExpire(ServerCouponDTO serverCouponDTO);
}
