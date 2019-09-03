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
    boolean beginFanYong(int BusinessType,String area,String userId, double money, double actualPayment,String orgId);

    /**
     * 洗车服务返佣操作
     */
    boolean beginWashCarFanYong(String userId);
}
