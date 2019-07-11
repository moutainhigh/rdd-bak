package com.cqut.czb.bn.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.petrolPriceReportDTO;
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

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    PetrolPriceReportMapperExtra petrolPriceReportMapperExtra;

    @Override
    public List<appAnnouncementDTO> selectAnnouncement(String locationCode) {
        if(locationCode==null&&locationCode.equals("")){
            System.out.println("locationCode为空");
            return null;
        }
        return announcementMapperExtra.selectAnnouncement(locationCode);
    }

    @Override
    public List<petrolPriceReportDTO> selectPetrolPriceReport(String area) {
        if(area==null&&area.equals("")){
            System.out.println("area为空");
            return null;
        }
        return petrolPriceReportMapperExtra.selectAll(area);
    }

    @Override
    public List<ServicePlan> selectServicePlan() {
        return servicePlanMapperExtra.selectServicePlan();
    }

    @Override
    public List<PetrolZoneDTO> selectPetrolZone(String area) {
        //从字典中查出对应油卡的描述
        Dict t=dictMapperExtra.selectDictByName("petrolRemark");
        JSONObject json = JSON.parseObject(t.getContent());
        List<PetrolZoneDTO> petrolZoneDTOList=petrolMapperExtra.selectPetrolZone(area);
        if(petrolZoneDTOList==null)
            return null;
        for( int i = 0 ; i < petrolZoneDTOList.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            System.out.println(petrolZoneDTOList.get(i));
            if(petrolZoneDTOList.get(i).getPetrolKind()==0){
                if(json.get("0")!=null)
                    petrolZoneDTOList.get(i).setPetrolRemark((String) json.get("0"));
                petrolZoneDTOList.get(i).setPetrolName("国通");
            }else if(petrolZoneDTOList.get(i).getPetrolKind()==1){
                if(json.get("1")!=null)
                petrolZoneDTOList.get(i).setPetrolRemark((String) json.get("1"));
                petrolZoneDTOList.get(i).setPetrolName("中石油");
            }else if(petrolZoneDTOList.get(i).getPetrolKind()==2) {
                if(json.get("2")!=null)
                petrolZoneDTOList.get(i).setPetrolRemark((String) json.get("2"));
                petrolZoneDTOList.get(i).setPetrolName("中石化");
            }
            }
        return petrolZoneDTOList;
    }

    @Override
    public boolean selectAllPetrol() {
        List<Petrol> petrols=petrolMapperExtra.selectPetrol();
        Map<String,Petrol> petrolMap=new ConcurrentHashMap<String,Petrol>();
        if (!CollectionUtils.isEmpty(petrols)){//判断所有的油卡是否为空
            for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
                if(PetrolCache.currentPetrolMap.containsKey(petrols.get(i).getPetrolNum())){
//                    System.out.println("MAP2中包含卡号"+petrols.get(i).getPetrolNum());
                    continue;
                }
//                System.out.println("MAP2中不包含卡号"+petrols.get(i).getPetrolNum());
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
