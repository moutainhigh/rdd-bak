package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.vehicleService.ServerStandardMapperExtra;
import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.service.AppCarWashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppCarWashImpl implements AppCarWashService {

    @Autowired
    ServerStandardMapperExtra serverStandardMapperExtra;

    @Override
    public List<ServiceCommodityDTO> SelectService() {
        return serverStandardMapperExtra.selectServiceInfo();
    }
}
