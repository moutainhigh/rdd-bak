package com.cqut.czb.bn.entity.dto.paymentRecord;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class GetAlipayClient {

	private  AlipayClient alipayClient;

//	private static boolean flag = true;//对于后面的功能拓展（如爱虎支付宝，支付宝，现默认为爱虎）

//	private static String operationType=null;//0表示支付宝支付，1表示充值

	private String callBackUrl = "";

	private static GetAlipayClient instance = new GetAlipayClient();

	private GetAlipayClient() {

	}

	public static void setInstance(GetAlipayClient instance) {
		GetAlipayClient.instance = instance;
	}

	public synchronized  static GetAlipayClient getInstance(String operationType) {
//		暂时与flag无关：只是用于后面功能拓展
		if (operationType=="0") {//"0"为购油
			instance.setAlipayClient(new DefaultAlipayClient(AiHuAlipayConfig.gatewayUrl, AiHuAlipayConfig.app_id,
					AiHuAlipayConfig.merchant_private_key, AiHuAlipayConfig.format, AiHuAlipayConfig.charset,
					AiHuAlipayConfig.alipay_public_key, AiHuAlipayConfig.sign_type));
			instance.setCallBackUrl(AiHuAlipayConfig.notify_url);
		} else if(operationType=="1"){//"1"代表的是充值vip
			instance.setAlipayClient(new DefaultAlipayClient(AiHuAlipayConfig.gatewayUrl, AiHuAlipayConfig.app_id,
					AiHuAlipayConfig.merchant_private_key, AiHuAlipayConfig.format, AiHuAlipayConfig.charset,
					AiHuAlipayConfig.alipay_public_key, AiHuAlipayConfig.sign_type));
			instance.setCallBackUrl(AiHuAlipayConfig.recharge_url);
		}else if(operationType=="2"){//"2"代表的是充值
			instance.setAlipayClient(new DefaultAlipayClient(AiHuAlipayConfig.gatewayUrl, AiHuAlipayConfig.app_id,
					AiHuAlipayConfig.merchant_private_key, AiHuAlipayConfig.format, AiHuAlipayConfig.charset,
					AiHuAlipayConfig.alipay_public_key, AiHuAlipayConfig.sign_type));
			instance.setCallBackUrl(AiHuAlipayConfig.PetrolRecharge_url);
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
