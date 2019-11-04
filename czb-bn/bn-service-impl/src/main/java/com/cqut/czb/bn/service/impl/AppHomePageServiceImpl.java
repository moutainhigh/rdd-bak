package com.cqut.czb.bn.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appHomePage.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppHomePageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

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
    UserMapperExtra userMapperExtra;

    @Autowired
    UserMapper userMapper;

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

    public AppRouterDTO getPic(AppRouterDTO appRouterDTO) {
        String code = appRouterDTO.getMenuIdentityCode();    //code也可改为由参数传入取得
        AppRouterDTO appRouter = new AppRouterDTO();
        appRouter.setMenuIdentityCode(code);
        List<AppRouterDTO> list = appRouterMapperExtra.selectAppRouterBycode(appRouter);
        if (list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public User selectVipUser(String userId) {

        while(true){
         User user=userMapper.selectByPrimaryKey(userId);
         if(user!=null){
             if (user.getIsVip() == 1) {
                 return user;
             }else {
                 if(user.getSuperiorUser()!=null&&!user.getSuperiorUser().equals("")) {
                     userId=user.getSuperiorUser();
                     continue;
                 }else {
                     return null;
                 }
             }
          }else {
             return null;
         }
        }
    }

    @Override
    public List<petrolPriceReportDTO> selectPetrolPriceReport(String area) {
        if(area==null||area.equals("")){
            area="重庆市";
        }
        return petrolPriceReportMapperExtra.selectAll(area);
    }

    @Override
    public List<ServicePlan> selectServicePlan() {
        return servicePlanMapperExtra.selectServicePlan();
    }

    //获取充值折扣
    public double getDisCount(String petrolRemark){
        //获取字典信息
        Dict dict=dictMapperExtra.selectDictByName("petrolPrice");
        //解析价格
        JSONObject json = JSON.parseObject(dict.getContent());
            //根据他的卡的是哪种油再取对应油的价格
        if(petrolRemark==null||petrolRemark.equals("")){
            petrolRemark="通用";
        }
        //取价格
        String content=(String) json.get(petrolRemark);
        if(content==null){
            return 1;
        }
        //分解价格
        String[] result1 = content.split(",");
        //取出折扣
        return Double.parseDouble(result1[result1.length-1]);
    }


    public List<PetrolZoneDTO> getPetrolZone(User user,String area){
        //判断用户买了哪些卡
        List<PetrolPriceDTO> petrolPriceDTOs=petrolMapperExtra.selectUserPetrol(user.getUserId(),area);
        //获取字典信息
        Dict dict=dictMapperExtra.selectDictByName("petrolPrice");
        //解析价格
        JSONObject json = JSON.parseObject(dict.getContent());

        for(int i=0;i<petrolPriceDTOs.size();i++){
            //根据他的卡的是哪种油再取对应油的价格
            String remark=petrolPriceDTOs.get(i).getRemark();
            if(remark==null||remark.equals("")){
                remark="通用";
                petrolPriceDTOs.get(i).setRemark("通用");
            }
            //取价格
            String content=(String) json.get(remark);
            if(content==null){
                return null;
            }
            //分解价格
            String[] result1 = content.split(",");
            //取出折扣
            petrolPriceDTOs.get(i).setDiscount(Double.parseDouble(result1[result1.length-1]));
            //取出价格，放入list
            List<Double> price=new ArrayList<>();
            for(int j=0;j<result1.length-1;j++){
                price.add(Double.valueOf(result1[j]));
            }
            petrolPriceDTOs.get(i).setPrice(price);
        }

        //查出所有的油卡
        List<PetrolZoneDTO> petrolZoneDTOList=selectPetrolZone(area);

        //更改油卡内容
        if(petrolPriceDTOs!=null){//不为空则表示买过卡
            //无需要考虑汽油还是柴油
            for(int i=0;i<petrolPriceDTOs.size();i++){
                //根据价格直接覆盖
                for(int j=0;j<petrolZoneDTOList.size();j++){
                    //将中石油，中石化价格覆盖(如果油卡的类型一样)
                    if(petrolZoneDTOList.get(j).getPetrolKind().equals(petrolPriceDTOs.get(i).getPetrolKind())){
                        List<petrolInfoDTO> petrolInfoDTOS=new ArrayList<>();
                        for(int k=0;k<petrolPriceDTOs.get(i).getPrice().size();k++){
                            //插入油卡信息
                            petrolInfoDTO petrolInfo=new petrolInfoDTO();
                            petrolInfo.setRemark(petrolPriceDTOs.get(i).getRemark());
                            petrolInfo.setFYmoney1(0);
                            petrolInfo.setFangyong1("0");
                            petrolInfo.setFangyong2("0");
                            petrolInfo.setPetrolPrice(petrolPriceDTOs.get(i).getPrice().get(k));
                            petrolInfo.setDiscount(petrolPriceDTOs.get(i).getDiscount());
                            petrolInfo.setPetrolDenomination(petrolPriceDTOs.get(i).getPrice().get(k));
                            BigDecimal money=BigDecimal.valueOf(petrolPriceDTOs.get(i).getDiscount()).multiply(new BigDecimal(petrolPriceDTOs.get(i).getPrice().get(k)));
                            double fee=money.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            petrolInfo.setVipPrice(fee);
                            petrolInfoDTOS.add(petrolInfo);
//                            petrolZoneDTOList.get(j).getPetrolPriceInfo().add(petrolInfo);
                        }
                        petrolZoneDTOList.get(j).setPetrolPriceInfo(petrolInfoDTOS);
                    }
                }
            }
        }
        return petrolZoneDTOList;
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
                //如果为空则设置为通用
                if(petrolInfoDTO1.get(j).getRemark()==null||"".equals(petrolInfoDTO1.get(j).getRemark())){
                    petrolInfoDTO1.get(j).setRemark("通用");
                }
                petrolInfoDTO1.get(j).setFangyong1(fangyong1);
                petrolInfoDTO1.get(j).setFangyong2(fangyong2);
                //算出vip价格
                if(petrolInfoDTO1.get(j).getDiscount()==0){
                    petrolInfoDTO1.get(j).setDiscount(1.0);
                }
                BigDecimal vipFee = BigDecimal.valueOf(petrolInfoDTO1.get(j).getDiscount()).multiply(new BigDecimal(petrolInfoDTO1.get(j).getPetrolPrice()));
                double money= vipFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                petrolInfoDTO1.get(j).setVipPrice(money);
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

    @Override
    public List<String> selectArea() {
        Dict isSelectAreaDict = dictMapperExtra.selectDictByName("is_select_area");
        if (isSelectAreaDict != null){
            String isSelectArea = isSelectAreaDict.getContent();
            if("true".equals(isSelectArea)){
                return petrolSaleConfigMapperExtra.getAllArea();
            }else{
                return null;
            }
        }
        return petrolSaleConfigMapperExtra.getAllArea();
    }

    @Override
    public List<PetrolStock> getPetrolStock(String area) {
        if(area == null){
            return null;
        }
        List<PetrolStock> petrolStocks = petrolMapperExtra.selectPetrolStock(area);
        Dict petrolStockSwitchDict = dictMapperExtra.selectDictByName("petrolStockSwitch");
        if(petrolStockSwitchDict != null){
            String body = petrolStockSwitchDict.getContent();
            Gson gson=new Gson();
            //序列化
            PetrolStockSwitch petrolStockSwitch= gson.fromJson(body,PetrolStockSwitch.class);
            if(petrolStockSwitch.getUseFakeData()){
                for(PetrolStock petrolStock : petrolStocks){
                    petrolStock.setShowMessage("今日库存剩余：" + petrolStockSwitch.getFakeTotal());
                }
                return petrolStocks;
            }
        }
        for(PetrolStock petrolStock : petrolStocks){
            petrolStock.setShowMessage("今日库存剩余：" + petrolStock.getTotal());
        }
        return petrolStocks;
    }

}
