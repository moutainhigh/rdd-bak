package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayNewClientConfig {

	private  AlipayClient alipayClient;

	private String callBackUrl = "";

	private static AlipayNewClientConfig instance = new AlipayNewClientConfig();

	private AlipayNewClientConfig() {

	}

	public static void setInstance(AlipayNewClientConfig instance) {
		AlipayNewClientConfig.instance = instance;
	}

	public synchronized  static AlipayNewClientConfig getInstance(String operationType) {
		if (operationType.equals("0")) {//"0"为购油
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
					AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.IntegralRecharge_url);
		}
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

}
