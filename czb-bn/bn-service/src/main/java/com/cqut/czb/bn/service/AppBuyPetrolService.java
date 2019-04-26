package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.entity.*;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/23
 * 作用：购买油卡
 */
public interface AppBuyPetrolService {

    /**
     * 更改油卡状态
     * @param petrol
     * @return
     */
    boolean updatePetrol(Petrol petrol);

    /**
     * 插入油卡购买记录
     * @param petrolSalesRecords
     * @return
     */
    boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords);

    /**
     * 插入收款记录表
     * @param depositRecords
     * @return
     */
    boolean insertDepositRecords(DepositRecords depositRecords);

    /**
     *
     * @param userIncomeInfo
     * @return
     */
    boolean insertUserIncomeInfo(UserIncomeInfo userIncomeInfo);

}
