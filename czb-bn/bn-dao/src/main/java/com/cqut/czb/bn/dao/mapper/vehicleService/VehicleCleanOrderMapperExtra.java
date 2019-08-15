package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppVehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleCleanOrderMapperExtra {

    List<VehicleCleanOrderDTO> selectById(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int updateOrderStateCancel(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int updateOrderStateComplete(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int insert(AppVehicleCleanOrderDTO record);

}
