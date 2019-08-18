package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppVehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleCleanOrderMapperExtra {

    List<VehicleCleanOrderDTO> selectById(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    VehicleCleanOrderDTO selectByStatus(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int updateOrderStateCancel(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int updateOrderStateComplete(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    int insert(AppVehicleCleanOrderDTO record);

    // 洗车订单管理页面查询
    Page<VehicleOrderManageDTO> search(VehicleOrderManageDTO dto);

    CleanRider getCleanRider(CleanRider rider);

    int updateRiderStatus(@Param("riderId") String riderId, @Param("status") String status);

    int updateMyBackOrder(AppVehicleCleanOrderDTO orderDTO);

    AppVehicleCleanOrderDTO selectByUserId(@Param("userId") String userId);

}
