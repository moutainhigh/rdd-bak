package com.cqut.czb.bn.service.PaymentProcess;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 支付验证
 * @author 陈德强
 */
public interface OrderVerifyService {

	//支付宝回调测试接口
	String AliOrderPayNotify(HttpServletRequest request,String consumptionType);

	//微信回调测试接口
	String WeChatOrderPayNotify(HttpServletRequest request,String consumptionType);

	//解析订单
	Map<String, String> parseOrder(Map<String, String> params, Map requestParams);

}
