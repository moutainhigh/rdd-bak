package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import com.cqut.czb.bn.entity.entity.ServicePlan;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人：陈德强
 */
@Service
public class AppHomePageServiceImpl implements AppHomePageService {

    @Autowired
    AnnouncementMapper announcementMapper;

    @Autowired
    AnnouncementMapperExtra announcementMapperExtra;

    @Autowired
    PetrolSaleConfigMapperExtra petrolSaleConfigMapperExtra;

    @Autowired
    PetrolSaleConfigMapper petrolSaleConfigMapper;

    @Autowired
    ServicePlanMapper servicePlanMapper;

    @Autowired
    ServicePlanMapperExtra servicePlanMapperExtra;

    @Autowired
    PetrolMapper petrolMapper;

    @Autowired
    PetrolMapperExtra petrolMapperExtra;

    @Override
    public List<appAnnouncementDTO> selectAnnouncement() {


        return announcementMapperExtra.selectAnnouncement();
    }

    @Override
    public List<PetrolSaleConfig> selectPetrolSaleConfig() {
        return petrolSaleConfigMapperExtra.selectPetrolSaleConfig();
    }

    @Override
    public List<ServicePlan> selectServicePlan() {
        return servicePlanMapperExtra.selectServicePlan();
    }

    @Override
    public List<PetrolZoneDTO> selectPetrolZone() {
        //读取所有的油卡存储下来进入map中
        AllPetrolDTO allPetrolDTO=new AllPetrolDTO(petrolMapperExtra.selectPetrol());
        return petrolMapperExtra.selectPetrolZone();
    }
}
