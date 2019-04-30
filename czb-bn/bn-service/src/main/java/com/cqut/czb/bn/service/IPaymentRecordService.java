package com.cqut.czb.bn.service;


import javax.servlet.http.HttpServletRequest;



/**
 * 缴费记录
 * 
 * @author yuanding
 *
 */
public interface IPaymentRecordService {

	/**
	 * 回调处理——先验证是否合理
	 * @param request
	 * @return
	 */
	String verifyAsynNoticeInfoAiHu(HttpServletRequest request);

}
