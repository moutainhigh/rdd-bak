package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    AppRouterMapperExtra appRouterMapperExtra;

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
        Map<String,Petrol> petrolMap=new ConcurrentHashMap<String,Petrol>();
        List<Petrol> petrols=petrolMapperExtra.selectPetrol();
        for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            petrolMap.put(petrols.get(i).getPetrolId(),petrols.get(i));
        }
        AllPetrolDTO allPetrolDTO=new AllPetrolDTO(petrolMap);
        return petrolMapperExtra.selectPetrolZone();
    }

    @Override
    public List<Petrol> selectAllPetrol() {
        //读取所有的油卡存储下来进入map中
//        AllPetrolDTO allPetrolDTO=new AllPetrolDTO(petrolMapperExtra.selectPetrol());
        return null;
    }

    @Override
    public List<AppRouter> selectHomePageRouters(AppRouter appRouter) {
        return appRouterMapperExtra.selectAppRouters(appRouter);
    }
}
