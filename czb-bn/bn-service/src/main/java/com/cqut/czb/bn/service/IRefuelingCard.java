package com.cqut.czb.bn.service;


import com.cqut.czb.bn.entity.entity.*;

import java.util.Map;

public interface IRefuelingCard {

	Map payCallback(int i, Object[] param);

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
	 * 插入收益变更表
	 */
	boolean insertIncomeLog(IncomeLog incomeLog);

	/**
	 * 修改收益信息表
	 */
	boolean updateUserIncomeInfo(UserIncomeInfo userIncomeInfo);
}
