package com.cqut.czb.bn.service.PaymentProcess;

import com.cqut.czb.bn.entity.entity.UserIncomeInfo;

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

    boolean changeUserIncomeInfo(String FyRemark, String commissionSourceUser, String commissionGotUser, double fangyongRate, UserIncomeInfo userIncomeInfo, double money, double actualPayment, String userId, int k, double FYrate, String orgId);

    /**
     * 小程序返佣
     * 返两级vip
     */
    boolean AppletBeginFanYong(String userId, double money,String orgId,double fyMoney);

    /**
     * 小程序返佣
     * 返两级vip
     */
    boolean AppletVIPBeginFanYong(String userId, double money,String orgId,double fyMoney);


}
