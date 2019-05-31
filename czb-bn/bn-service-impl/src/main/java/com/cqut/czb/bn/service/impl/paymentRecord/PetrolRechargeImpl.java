package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.PetrolMapperExtra;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.service.petrolRecharge.FanYongService;
import com.cqut.czb.bn.service.petrolRecharge.PetrolRecharge;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetrolRechargeImpl implements PetrolRecharge {

    @Autowired
    PetrolMapperExtra petrolMapperExtra;

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    FanYongService fanYongService;

    @Override
    public boolean beginPetrolRecharge(String thirdOrderId,double money, String petrolNum,
                                       String ownerId, double actualPayment,String orgId)
    {
        //通过商家订单号查询充值信息
        PetrolSalesRecords petrolSalesRecords=new PetrolSalesRecords();
        petrolSalesRecords=petrolSalesRecordsMapperExtra.selectInfoByOrgId(orgId);
        if(petrolSalesRecords==null){
            System.out.println("无充值信息");
            return false;
        }
        //更改油卡购买信息的状态
        petrolSalesRecords.setThirdOrderId(thirdOrderId);
        petrolSalesRecords.setState(1);
        boolean update=petrolSalesRecordsMapperExtra.updateByPrimaryKeySelective(petrolSalesRecords)>0;
        System.out.println("更改购买信息:"+update);

        //开始返佣
        System.out.println("****油卡充值开始返佣****");
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,actualPayment,orgId);

        if(beginFanYong==true)
            return true;
        else {
            System.out.println("新增购买记录表有问题或返佣");
            return false;
        }
    }
}
