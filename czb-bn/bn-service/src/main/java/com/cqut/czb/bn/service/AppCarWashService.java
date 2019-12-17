package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.conpons;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;

import java.util.List;

public interface AppCarWashService {
    /**
     * 查找服务
     */
    List<ServiceCommodityDTO> SelectService();


    /**
     * 获取已录信息
     */
    CleanServerVehicle selectCleanServerVehicle(String userId);

    /**
     * 获取一条服务信息
     */
    ServiceCommodityDTO selectOneService(String serviceId);

    /**
     * 用户须知
     */
    List<Dict> getUserInstruction();

    /**
     * 获取优惠劵
     */
    List<conpons> getCoupons(String userId,String couponId);
}
