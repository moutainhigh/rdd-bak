package com.cqut.czb.bn.service;


import com.cqut.czb.bn.entity.entity.*;

import java.util.Map;

public interface IRefuelingCard {

	/**
	 * 购买油卡支付成功后的处理
	 * @param i
	 * @param param
	 * @return
	 */
	Map payCallback(Object[] param);

	/**
	 * 购买油卡支付失败后的处理
	 * @param param
	 * @return
	 */
	void purchaseFailed(Object[] param);

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

}
