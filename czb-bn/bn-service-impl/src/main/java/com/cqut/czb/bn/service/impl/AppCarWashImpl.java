package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanServerVehicleMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerStandardMapperExtra;
import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.conpons;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
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

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;

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

    @Override
    public Dict getUserInstruction() {
        return dictMapperExtra.selectDictByName("infoToUser");
    }

    @Override
    public List<conpons> getCoupons(String userId,String couponId) {
        return serverCouponMapperExtra.selectCoupons( userId, couponId);
    }
}
