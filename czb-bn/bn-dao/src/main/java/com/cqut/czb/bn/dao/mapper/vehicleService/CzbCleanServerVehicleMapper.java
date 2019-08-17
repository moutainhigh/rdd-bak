package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbCleanServerVehicle;

public interface CzbCleanServerVehicleMapper {
    int deleteByPrimaryKey(String vehicleId);

    int insert(CzbCleanServerVehicle record);

    int insertSelective(CzbCleanServerVehicle record);

    CzbCleanServerVehicle selectByPrimaryKey(String vehicleId);

    int updateByPrimaryKeySelective(CzbCleanServerVehicle record);

    int updateByPrimaryKey(CzbCleanServerVehicle record);
}