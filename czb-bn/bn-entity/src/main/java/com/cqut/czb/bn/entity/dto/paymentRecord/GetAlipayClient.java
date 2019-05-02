package com.cqut.czb.bn.entity.dto.paymentRecord;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class GetAlipayClient {

	private  AlipayClient alipayClient;

	private static boolean flag = true;//对于后面的功能拓展（如爱虎支付宝，支付宝，现默认为爱虎）

	private String callBackUrl = "";

	private static GetAlipayClient instance = new GetAlipayClient();

	private GetAlipayClient() {

	}

	public static void setInstance(GetAlipayClient instance) {
		GetAlipayClient.instance = instance;
	}

	public synchronized  static GetAlipayClient getInstance() {
//		暂时与flag无关：只是用于后面功能拓展
		if (flag) {
			instance.setAlipayClient(new DefaultAlipayClient(AiHuAlipayConfig.gatewayUrl, AiHuAlipayConfig.app_id,
					AiHuAlipayConfig.merchant_private_key, AiHuAlipayConfig.format, AiHuAlipayConfig.charset,
					AiHuAlipayConfig.alipay_public_key, AiHuAlipayConfig.sign_type));
			instance.setCallBackUrl(AiHuAlipayConfig.notify_url);
//			instance.setAlipayClient(new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
//					AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset,
//					AlipayConfig.alipay_public_key, AlipayConfig.sign_type));
//			instance.setCallBackUrl(AlipayConfig.notify_url);
		} else {
			instance.setAlipayClient(new DefaultAlipayClient(AiHuAlipayConfig.gatewayUrl, AiHuAlipayConfig.app_id,
					AiHuAlipayConfig.merchant_private_key, AiHuAlipayConfig.format, AiHuAlipayConfig.charset,
					AiHuAlipayConfig.alipay_public_key, AiHuAlipayConfig.sign_type));
			instance.setCallBackUrl(AiHuAlipayConfig.notify_url);
		}
		flag = !flag;
		return instance;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public AlipayClient getAlipayClient() {
		return alipayClient;
	}

	public void setAlipayClient(AlipayClient alipayClient) {
		this.alipayClient = alipayClient;
	}

	public static boolean isFlag() {
		return flag;
	}

	public static void setFlag(boolean flag) {
		GetAlipayClient.flag = flag;
	}

}
