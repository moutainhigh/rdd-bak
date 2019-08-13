package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;

import java.util.List;

public interface VehicleCleanOrderMapperExtra {

    List<VehicleCleanOrderDTO> selectById(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int updateOrderStateCancel(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int updateOrderStateComplete(VehicleCleanOrderDTO vehicleCleanOrderDTO);

}
