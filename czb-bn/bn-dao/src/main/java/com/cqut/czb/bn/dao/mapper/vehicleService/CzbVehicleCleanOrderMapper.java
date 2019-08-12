package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbVehicleCleanOrder;

public interface CzbVehicleCleanOrderMapper {
    int deleteByPrimaryKey(String serverOrderId);

    int insert(CzbVehicleCleanOrder record);

    int insertSelective(CzbVehicleCleanOrder record);

    CzbVehicleCleanOrder selectByPrimaryKey(String serverOrderId);

    int updateByPrimaryKeySelective(CzbVehicleCleanOrder record);

    int updateByPrimaryKey(CzbVehicleCleanOrder record);
}