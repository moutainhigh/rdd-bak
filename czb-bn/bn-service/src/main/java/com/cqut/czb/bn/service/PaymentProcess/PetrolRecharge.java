package com.cqut.czb.bn.service.PaymentProcess;

public interface PetrolRecharge {
    /**
     * 油卡充值
     */
    boolean beginPetrolRecharge(String area,String thirdOrderId,double money,String petrolNum,
                                String ownerId,double actualPayment,
                                String orgId);
}
