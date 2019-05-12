package com.cqut.czb.bn.service.petrolRecharge;

public interface PetrolRecharge {
    /**
     * 油卡充值
     */
    boolean beginPetrolRecharge(double money,int count,
                                int petrolKind,String petrolNum,
                                String ownerId,double actualPayment,
                                String orgId);
}
