package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.PetrolMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanServerVehicleMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerStandardMapperExtra;
import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.conpons;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.service.AppCarWashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    PetrolSalesRecordsMapper petrolSalesRecordsMapper;

    @Autowired
    PetrolMapper petrolMapper;

    @Override
    public List<ServiceCommodityDTO> SelectService() {

//        // 插入数据油卡销售
//       List<PetrolSalesRecords> list= petrolSalesRecordsMapper.selectAll();
//       double denomination=0;
//       double currentPrice=0;
//       for (int i=0;i<list.size();i++){
//           //如果是国通卡
//           if(list.get(i).getPetrolKind()==0){
//               //通过油卡号查询出相关信息
//               Petrol petrol=petrolMapper.selectByPrimaryKey(list.get(i).getPetrolId());
//               denomination=petrol.getPetrolDenomination();
//               currentPrice=list.get(i).getTurnoverAmount();
//               list.get(i).setDenomination(denomination);
//               list.get(i).setCurrentPrice(currentPrice);
//               petrolSalesRecordsMapper.updateMenus(list.get(i));
//               System.out.println(i);
//           }else if(list.get(i).getPetrolKind()==1){
//               denomination=getBAI(list.get(i).getTurnoverAmount());
//               currentPrice=list.get(i).getTurnoverAmount();
//               list.get(i).setDenomination(denomination);
//               list.get(i).setCurrentPrice(currentPrice);
//               petrolSalesRecordsMapper.updateMenus(list.get(i));
//               System.out.println(i);
//           }
//       }
//        //插入数据


       return serverStandardMapperExtra.selectServiceInfo();
//        return null;
    }

    //求解大于某个数最小的百位数
    public int getBAI(double num){
       if(num<=100){
           return 100;
       }else if(num>100&&num<=200){
           return 200;
       }else if(num>200&&num<=300){
           return 300;
       }else if(num>300&&num<=400){
           return 400;
       }else if(num>400&&num<=500){
           return 500;
       }else if(num>500&&num<=600){
           return 600;
       }else if(num>600&&num<=700){
           return 700;
       }else if(num>700&&num<=800){
           return 800;
       }else if(num>800&&num<=900){
           return 900;
       }else if(num>900&&num<=1000){
           return 1000;
       }else {
           return 0;
       }
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
    public List<Dict> getUserInstruction() {
        List<Dict> list = new ArrayList<>();
        Dict infoToUser = dictMapperExtra.selectDictByName("infoToUser");
        Dict carWashingRules = dictMapperExtra.selectDictByName("carWashingRules");
        list.add(infoToUser);
        list.add(carWashingRules);
        return list;
    }

    @Override
    public List<conpons> getCoupons(String userId,String couponId) {
        return serverCouponMapperExtra.selectCoupons( userId, couponId);
    }
}
