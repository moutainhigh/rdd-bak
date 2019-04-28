package com.cqut.czb.bn.service.impl.paymentRecord;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cqut.czb.bn.service.impl.paymentRecord.AiHuAlipayConfig;

public class GetAlipayClient {
	private  AlipayClient alipayClient;

	static boolean flag = true;
	private static GetAlipayClient instance = new GetAlipayClient();

	public synchronized  static GetAlipayClient getInstance() {
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

	private String callBackUrl = "";

	private GetAlipayClient() {

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

}
