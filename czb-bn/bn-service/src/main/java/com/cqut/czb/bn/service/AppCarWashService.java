package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;

import java.util.List;

public interface AppCarWashService {
    /**
     * 查找服务
     * @return
     */
    List<ServiceCommodityDTO> SelectService();


    /**
     * 获取已录信息
     */
    CleanServerVehicle selectCleanServerVehicle(String userId);

    ServiceCommodityDTO selectOneService(String serverId);
}
