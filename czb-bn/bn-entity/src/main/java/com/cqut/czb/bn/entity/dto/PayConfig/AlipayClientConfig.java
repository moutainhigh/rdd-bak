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
		if (operationType.equals("0")) {//"0"为购油
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
		} else if(operationType.equals("1")){//"1"代表的是充值vip
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
		}else if(operationType.equals("2")){//"2"代表的是充值
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
		}else if(operationType.equals("3")){//"3"代表的是购买服务
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.BuyService_url);
		}else if(operationType.equals("4")){//"4"代表的是洗车服务
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.BuyCarWash_url);
		}else if(operationType.equals("5")){//"5"代表的是点餐
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.BuyDish_url);
		}else if(operationType.equals("6")){//"6"代表中石化优惠券
			instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
					AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
					AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
			instance.setCallBackUrl(AliPayConfig.Coupons_url);
		}
		return instance;
	}
	public synchronized  static AlipayClientConfig getInstance(String operationType,Integer isSpecial) {
		if (operationType.equals("0")) {//"0"为购油
			if (isSpecial == 1){
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
						AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
			}else {
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
						AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
			}
			instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
		} else if(operationType.equals("1")){//"1"代表的是充值vip
			if (isSpecial == 1){
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
						AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}else {
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
						AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}
			instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
		}else if(operationType.equals("2")){//"2"代表的是充值
			if (isSpecial == 1){
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
						AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
			}else {
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
						AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
			}
			instance.setCallBackUrl(AliPayConfig.PetrolRecharge_url);
		}else if(operationType.equals("4")){//"4"代表的是洗车服务
			if (isSpecial == 1){
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
						AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}else {
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
						AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}
			instance.setCallBackUrl(AliPayConfig.BuyCarWash_url);
		}else if(operationType.equals("5")){//"5"代表的是点餐
			if (isSpecial == 1){
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
						AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}else {
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
						AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}
			instance.setCallBackUrl(AliPayConfig.BuyDish_url);
		}else if(operationType.equals("6")){//"6"代表中石化优惠券
			if (isSpecial == 1){
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id_new,
						AliPayConfig.merchant_private_key_new, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key_new, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}else {
				instance.setAlipayClient(new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
						AliPayConfig.merchant_private_key, AliPayConfig.format, AliPayConfig.charset,
						AliPayConfig.alipay_public_key, AliPayConfig.sign_type));
//				instance.setCallBackUrl(AliPayConfig.RechargeVip_url);
			}
			instance.setCallBackUrl(AliPayConfig.Coupons_url);
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
