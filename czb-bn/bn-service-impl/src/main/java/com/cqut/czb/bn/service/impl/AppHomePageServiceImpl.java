package com.cqut.czb.bn.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.petrolInfoDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.petrolPriceReportDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.ServicePlan;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
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

        Dict remarks=new Dict();//保存油卡的描述
        Dict FY1=new Dict();//保存一级返佣比例
        Dict FY2=new Dict();//保存二级返佣比例
        //从字典中查出对应油卡的描述
        List<Dict> infos=dictMapperExtra.selectPetrolInfo();

        if(infos!=null){
            for (int i=0;i<infos.size();i++){
                if(infos.get(i).getName().equals("petrolRemark")){
                    remarks=infos.get(i);
                    continue;
                }
                if(infos.get(i).getName().equals("fangyong1")){
                    FY1=infos.get(i);
                    continue;
                }
                if(infos.get(i).getName().equals("fangyong2")){
                    FY2=infos.get(i);
                    continue;
                }
            }
        }
        JSONObject json = JSON.parseObject(remarks.getContent());
        List<PetrolZoneDTO> petrolZoneDTOList=petrolMapperExtra.selectPetrolZone(area);
        if(petrolZoneDTOList==null)
            return null;
        for( int i = 0 ; i < petrolZoneDTOList.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。

            if(petrolZoneDTOList.get(i).getPetrolKind()==0){
                if(json.get("0")!=null)
                    petrolZoneDTOList.get(i).setPetrolRemark((String) json.get("0"));
                petrolZoneDTOList.get(i).setPetrolName("国通");
                //插入每张油卡的折扣信息和返佣信息
                List<petrolInfoDTO> petrolInfoDTO1= calculatePrice(petrolZoneDTOList.get(i).getPetrolPriceInfo(),FY1.getContent(),FY2.getContent());
                if(petrolInfoDTO1!=null){
                    petrolZoneDTOList.get(i).setPetrolPriceInfo(petrolInfoDTO1);
                }

            }else if(petrolZoneDTOList.get(i).getPetrolKind()==1){
                if(json.get("1")!=null)
                    petrolZoneDTOList.get(i).setPetrolRemark((String) json.get("1"));
                petrolZoneDTOList.get(i).setPetrolName("中石油");
                //插入每张油卡的折扣信息和返佣信息
                List<petrolInfoDTO> petrolInfoDTO1= calculatePrice(petrolZoneDTOList.get(i).getPetrolPriceInfo(),FY1.getContent(),FY2.getContent());
                if(petrolInfoDTO1!=null){
                    petrolZoneDTOList.get(i).setPetrolPriceInfo(petrolInfoDTO1);
                }
            }else if(petrolZoneDTOList.get(i).getPetrolKind()==2) {
                if(json.get("2")!=null)
                    petrolZoneDTOList.get(i).setPetrolRemark((String) json.get("2"));
                petrolZoneDTOList.get(i).setPetrolName("中石化");
                //插入每张油卡的折扣信息和返佣信息
                List<petrolInfoDTO> petrolInfoDTO1= calculatePrice(petrolZoneDTOList.get(i).getPetrolPriceInfo(),FY1.getContent(),FY2.getContent());
                if(petrolInfoDTO1!=null){
                    petrolZoneDTOList.get(i).setPetrolPriceInfo(petrolInfoDTO1);
                }
            }
            }
        return petrolZoneDTOList;
    }

    public List<petrolInfoDTO> calculatePrice(List<petrolInfoDTO> petrolInfoDTO1, String fangyong1, String fangyong2){
        if(petrolInfoDTO1!=null) {
            for (int j = 0; j < petrolInfoDTO1.size(); j++) {
                petrolInfoDTO1.get(j).setFangyong1(fangyong1);
                petrolInfoDTO1.get(j).setFangyong2(fangyong2);
                //算出vip价格
                double vipFee = BigDecimal.valueOf(petrolInfoDTO1.get(j).getDiscount()).multiply(new BigDecimal(petrolInfoDTO1.get(j).getPetrolPrice()))
                        .doubleValue();
                petrolInfoDTO1.get(j).setVipPrice(vipFee);
                if(fangyong1!=null)
                {
                    //算出一级返佣的钱
                    double FY1fee = BigDecimal.valueOf(Double.valueOf(petrolInfoDTO1.get(j).getFangyong1())).multiply(new BigDecimal(petrolInfoDTO1.get(j).getPetrolPrice()))
                            .doubleValue();
                    petrolInfoDTO1.get(j).setFYmoney1(FY1fee);
                }
                if(fangyong2!=null){
                    //算出二级返佣的钱
                    double FY2fee = BigDecimal.valueOf(Double.valueOf(petrolInfoDTO1.get(j).getFangyong2())).multiply(new BigDecimal(petrolInfoDTO1.get(j).getPetrolPrice()))
                            .doubleValue();
                    petrolInfoDTO1.get(j).setFYmoney2(FY2fee);
                }
            }
        }
        return petrolInfoDTO1;
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
