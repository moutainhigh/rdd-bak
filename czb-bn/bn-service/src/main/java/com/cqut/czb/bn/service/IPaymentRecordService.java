package com.cqut.czb.bn.service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 支付验证
 * @author 陈德强
 */
public interface IPaymentRecordService {

	/**
	 * 回调处理——先验证是否合理
	 * @param request
	 * @return
	 */
	String verifyAsynNoticeInfoAiHu(HttpServletRequest request);

	/**
	 * 购买服务回调处理
	 */
	String verifyBuyServiceAiLi(HttpServletRequest request);


	/**
	 * 购油回调处理(微信)
	 */
	String orderPayNotify(HttpServletRequest request);

	/**
	 * 购买服务回调处理(微信)
	 */
	String buyServiceOrderPayNotify(HttpServletRequest request);

	//解析订单
	Map<String, String> parseOrder(Map<String, String> params, Map requestParams);
}
