package com.cqut.czb.bn.service.PaymentProcess;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;

import java.util.Map;

public interface DataProcessService {


    //插入VIP充值记录表
    void insertRechargeOrder(double money, int payType, String orgId, String thirdOrderId, String ownerId, String vipAreaConfigId);

    //查询是否首次消费
    void isHaveConsumption(String ownerId);

    //payType对应0为油卡购买，1为油卡充值,2为购买服务
    //插入消费记录
    void insertConsumptionRecord(String orgId, String thirdOrderId, double money, String ownerId, String businessType, int payMethod);

    //解析订单数据用于处理（成功此块有点冗余）
    PetrolSalesRecordsDTO getOrderdata(Map<String, String> params);

    //油卡表——更改相应油卡的状态（用户的id，卡号）——更改
    boolean updatePetrol(Petrol petrol);

    //新增购买记录表——插入
    boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords);

    boolean changeInfo(String thirdOrderId, double money, String petrolNum, String ownerId, double actualPayment, String addressId, String orgId);

    //放回油卡
    int putBackPetrol(boolean isSucceed, String petrolNum);
}
