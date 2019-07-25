package com.cqut.czb.bn.service.PaymentProcess;


import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;

import java.util.Map;

public interface BusinessProcessService {

	/**
	 * 支付宝处理接口
	 * @param param
	 * @param consumptionType
	 * @return
	 */
	Map AliPayback(Object[] param, String consumptionType);

	/**
	 * 微信处理接口
	 * @param param
	 * @param consumptionType
	 * @return
	 */
	Map WeChatPayBack(Object[] param,String consumptionType);

	/**
	 * 购买油卡支付失败后的处理
	 * @param param
	 * @return
	 */
	void purchaseFailed(Object[] param);

}
