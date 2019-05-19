package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppHomePageService;
import org.apache.commons.collections4.list.PredicatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
        return petrolMapperExtra.selectPetrolZone();
    }

    @Override
    public boolean selectAllPetrol() {
        List<Petrol> petrols=petrolMapperExtra.selectPetrol();
        Map<String,Petrol> petrolMap=new ConcurrentHashMap<String,Petrol>();
        if (!CollectionUtils.isEmpty(petrols)){//判断所有的油卡是否为空
            for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
                if(PetrolCache.currentPetrolMap.containsKey(petrols.get(i).getPetrolNum())){
                    System.out.println("MAP2中包含卡号"+petrols.get(i).getPetrolNum());
                    continue;
                }
                System.out.println("MAP2中不包含卡号"+petrols.get(i).getPetrolNum());
                petrolMap.put(petrols.get(i).getPetrolId(),petrols.get(i));
            }
        }else {
            return false;
        }
        PetrolCache.AllpetrolMap=petrolMap;
        return true;
    }

    @Override
    public List<AppRouterDTO> selectHomePageRouters(AppRouterDTO appRouterDTO) {
        return appRouterMapperExtra.selectAppRouters(appRouterDTO);
    }

    @Override
    public List<PetrolInfoDTO> selectPetrolInfoDTO() {
        List<PetrolInfoDTO> petrolInfoDTOList=new ArrayList<PetrolInfoDTO>();
        petrolInfoDTOList=petrolMapperExtra.selectPetrolInfoDTO();
        for(int i=0;i<petrolInfoDTOList.size();i++) {
            if (petrolInfoDTOList.get(i).getPetrolKind() == 0)
                petrolInfoDTOList.get(i).setPetrolName("大重庆加油卡");
            else if (petrolInfoDTOList.get(i).getPetrolKind() == 1) {
                petrolInfoDTOList.get(i).setPetrolName("全国石油卡");
            } else if (petrolInfoDTOList.get(i).getPetrolKind() == 2) {
                petrolInfoDTOList.get(i).setPetrolName("全国石化卡");
            }
        }
        return petrolInfoDTOList;
    }
}
