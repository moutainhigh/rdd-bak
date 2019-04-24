package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.AnnouncementMapper;
import com.cqut.czb.bn.dao.mapper.PetrolMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSaleConfigMapper;
import com.cqut.czb.bn.dao.mapper.ServicePlanMapper;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import com.cqut.czb.bn.entity.entity.ServicePlan;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppHomePageServiceImpl implements AppHomePageService {

    @Autowired
    AnnouncementMapper announcementMapper;

    @Autowired
    PetrolSaleConfigMapper petrolSaleConfigMapper;

    @Autowired
    ServicePlanMapper servicePlanMapper;

    @Autowired
    PetrolMapper petrolMapper;

    @Override
    public List<Announcement> selectAnnouncement() {
        return announcementMapper.selectAnnouncement();
    }

    @Override
    public List<PetrolSaleConfig> selectPetrolSaleConfig() {
        return petrolSaleConfigMapper.selectPetrolSaleConfig();
    }

    @Override
    public List<ServicePlan> selectServicePlan() {
        return servicePlanMapper.selectServicePlan();
    }

    @Override
    public List<PetrolZoneDTO> selectPetrolZone() {
        return petrolMapper.selectPetrolZone();
    }
}
