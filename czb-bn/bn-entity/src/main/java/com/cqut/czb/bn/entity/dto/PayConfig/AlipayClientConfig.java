package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayClientConfig {

	private  AlipayClient alipayClient;

	private String callBackUrl = "";

	private static AlipayClientConfig instance = new AlipayClientConfig();

	private AlipayClientConfig() {

	}

	public static void setInstance(AlipayClientConfig instance) {
		AlipayClientConfig.instance = instance;
	}

	public synchronized  static AlipayClientConfig getInstance(String operationType) {
		if (operationType=="0") {//"0"为购油
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.notify_url);
		} else if(operationType=="1"){//"1"代表的是充值vip
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
		}else if(operationType=="2"){//"2"代表的是充值
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
		}else if(operationType=="3"){//"3"代表的是购买服务
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.BuyService_url);
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
