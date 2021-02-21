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
		if (operationType.equals("0")) {//"0"为购买积分
			instance.setAlipayClient(new DefaultAlipayClient(AliPayH5Config.gatewayUrl, AliPayH5Config.app_id,
					AliPayH5Config.merchant_wap_private_key, AliPayH5Config.format, AliPayH5Config.charset,
					AliPayH5Config.alipay_wap_public_key, AliPayH5Config.sign_type));
			instance.setCallBackUrl(AliPayH5Config.IntegralRecharge_url);
		}else if(operationType.equals("1")) {//"1"为库存商品购买
			instance.setAlipayClient(new DefaultAlipayClient(AliPayH5Config.gatewayUrl, AliPayH5Config.app_id,
					AliPayH5Config.merchant_wap_private_key, AliPayH5Config.format, AliPayH5Config.charset,
					AliPayH5Config.alipay_wap_public_key, AliPayH5Config.sign_type));
			instance.setCallBackUrl(AliPayH5Config.IntegralRecharge_url);
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
