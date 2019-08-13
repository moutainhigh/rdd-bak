package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanServerVehicleMapper {
    int deleteByPrimaryKey(String vehicleId);

    int insert(CleanServerVehicle record);

    int insertSelective(CleanServerVehicle record);

    CleanServerVehicle selectByPrimaryKey(String vehicleId);

    int updateByPrimaryKeySelective(CleanServerVehicle record);

    int updateByPrimaryKey(CleanServerVehicle record);
}