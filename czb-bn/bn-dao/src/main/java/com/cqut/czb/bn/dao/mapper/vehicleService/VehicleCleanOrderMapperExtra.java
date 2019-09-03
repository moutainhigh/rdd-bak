package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppVehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ImageInfoDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
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

    // 洗车订单管理获取骑手信息
    CleanRider getCleanRider(CleanRider rider);

    List<CleanRider> getCleanRiders();

    // 洗车订单管理，更改骑手状态
    int updateRiderStatus(@Param("riderId") String riderId, @Param("status") String status);

    // 洗车订单管理，查找图片url
    List<ImageInfoDTO> getUrls(@Param("serverOrderId") String serverOrderId, @Param("status") String  status);
    int updateMyBackOrder(AppVehicleCleanOrderDTO orderDTO);

    // 洗车订单管理，删除图片关系表
    int deleteImageRelation(@Param("fileId") String fileId);

    // 优惠券状态修改
    int updateCouponStatus(@Param("couponId") String couponId);

    // 插入图片订单关系
    int insertImageRelation(@Param("serverOrderId") String serverOrderId, @Param("fileId") String fileId, @Param("relationId") String relationId, @Param("status") String status);

    AppVehicleCleanOrderDTO selectByUserId(@Param("userId") String userId);

}
