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
    public boolean beginPetrolRecharge(double money, int count,
                                       int petrolKind, String petrolNum,
                                       String ownerId, double actualPayment,String orgId)
    {
        //通过油卡号查出用户买的油卡信息
        Petrol petrol= new Petrol();
        petrol.setOwnerId(ownerId);
        petrol.setPetrolKind(petrolKind);
        petrol=petrolMapperExtra.selectMyPetrol(petrol);
        if(petrol==null){
            System.out.println("没有买过油卡");
            return false;
        }
        //新增购买记录表——插入
        PetrolSalesRecords petrolSalesRecords=new PetrolSalesRecords();
        petrolSalesRecords.setPetrolId(petrol.getPetrolId());
        petrolSalesRecords.setBuyerId(ownerId);
        petrolSalesRecords.setPaymentMethod(1);//1为支付宝支付
        petrolSalesRecords.setPetrolKind(petrolKind);//油卡种类
        petrolSalesRecords.setPetrolNum(petrolNum);//卡号
        petrolSalesRecords.setRecordId(StringUtil.createId());
        petrolSalesRecords.setState(1);//1为已支付
        petrolSalesRecords.setTurnoverAmount(petrol.getPetrolPrice());
        petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());
        petrolSalesRecords.setThirdOrderId(orgId);
        petrolSalesRecords.setRecordType(petrol.getPetrolType());
        petrolSalesRecords.setIsRecharged(1);
        boolean insertPetrolSalesRecords=petrolSalesRecordsMapperExtra.insert(petrolSalesRecords)>0;
        System.out.println("新增油卡充值记录完毕"+insertPetrolSalesRecords);

        //开始返佣
        System.out.println("油卡充值开始返佣");
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,actualPayment,orgId);

        if(beginFanYong==true&&insertPetrolSalesRecords==true)
            return true;
        else {
            System.out.println("新增购买记录表有问题或返佣");
            return false;
        }
    }
}
