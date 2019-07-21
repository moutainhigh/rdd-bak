package com.cqut.czb.bn.service;


import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;

import java.util.Map;

public interface IRefuelingCard {

	/**
	 * 购买（或充值)油卡支付成功后的处理(支付宝)
	 * @param param
	 * @return
	 */
	Map AliPayCallback(Object[] param);

	/**
	 * 购买服务支付成功后的处理(支付宝)
	 * @param param
	 * @return
	 */
	Map AliBuyServicePayCallback(Object[] param);

    /**
     * 购买(或充值)油卡支付成功后的处理(微信)
     * @param param
     * @return
     */
    Map WeChatPayCallback(Object[] param);

	/**
	 * 购买服务（微信回调处理）
	 */
	Map WeChatBuyServicePayCallback(Object[] param);

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
