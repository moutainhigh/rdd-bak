package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import org.springframework.stereotype.Component;

@Component
public interface VehicleCleanOrderMapper {
    int deleteByPrimaryKey(String serverOrderId);

    int insert(VehicleCleanOrder record);

    int insertSelective(VehicleCleanOrder record);

    VehicleCleanOrder selectByPrimaryKey(String serverOrderId);

    int updateByPrimaryKeySelective(VehicleCleanOrder record);

    int updateByPrimaryKey(VehicleCleanOrder record);
}