package com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.CallBack;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import com.cqut.czb.bn.service.ThirdBusinessService.ChangeOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 插入订单信息
 */
@Service
public class ChangeOrderInfo implements ChangeOrderInfoService {

    @Autowired
    private PetrolCouponsSalesRecordsMapperExtra extra;

    @Override
    public void updateOrderInfo(Map<String, String> params){

        PetrolCouponsSalesRecords records=new PetrolCouponsSalesRecords();
        if(params.get("OrderId")!=null){
            records.setOrderId(params.get("OrderId"));
        }
        if(params.get("OutID")!=null){
            records.setToRddOutId(params.get("OutID"));
        }
        if(params.get("Account")!=null){
            records.setUserAccount(params.get("Account"));
        }
        if(params.get("OrderInfo")!=null){
            records.setOrderInfo(params.get("OrderInfo"));
        }
        records.setToLuPayState(1);
        records.setToLuPayEndTime(new Date());
        records.setUpdateAt(new Date());
        int is= extra.updateByLuPayInfo(records);
        System.out.println("插入璐付回调信息"+(is>0));
    }

}
