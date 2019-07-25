package com.cqut.czb.bn.service.PaymentProcess;

/**
 * author:陈德强
 * 作用：返佣操作
 */
public interface FanYongService {

    /**
     * 购买油卡返佣操作
     * @param userId
     * @param money
     * @param actualPayment
     * @return
     */
    boolean beginFanYong(String userId, double money, double actualPayment,String orgId);

}
