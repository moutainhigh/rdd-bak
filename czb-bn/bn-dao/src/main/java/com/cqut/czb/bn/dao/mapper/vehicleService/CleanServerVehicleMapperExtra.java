package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppCleanServerVehicleDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanServerVehicleMapperExtra {

    int insert(AppCleanServerVehicleDTO record);

    CleanServerVehicle selectCleanServerVehicle(@Param("userId") String userId);

    int updateByUserId(AppCleanServerVehicleDTO record);
}