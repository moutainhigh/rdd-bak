package com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.CallBack;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * 插入订单信息
 */
public class ChangeOrderInfo {

    @Autowired
    private PetrolCouponsSalesRecordsMapperExtra extra;

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
        System.out.println("插入支付宝起吊信息"+(is>0));
    }

}
