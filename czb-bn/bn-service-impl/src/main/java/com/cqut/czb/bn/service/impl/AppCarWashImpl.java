package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.vehicleService.CleanServerVehicleMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerStandardMapperExtra;
import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.service.AppCarWashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppCarWashImpl implements AppCarWashService {

    @Autowired
    ServerStandardMapperExtra serverStandardMapperExtra;

    @Autowired
    CleanServerVehicleMapperExtra cleanServerVehicleMapperExtra;

    @Override
    public List<ServiceCommodityDTO> SelectService() {
        return serverStandardMapperExtra.selectServiceInfo();
    }

    @Override
    public CleanServerVehicle selectCleanServerVehicle(String userId) {
        return cleanServerVehicleMapperExtra.selectCleanServerVehicle(userId);
    }

    @Override
    public ServiceCommodityDTO selectOneService(String serverId) {
        return serverStandardMapperExtra.selectOneService(serverId);
    }
}