package com.cqut.czb.bn.entity.dto.paymentRecord;

/**
 * 
 * @author chendeqiang
 *
 */
public class WXPayConfig {

	// 密钥key
	public static final String key = "CHONGQINGAiDonginformation201808";

	// 公众账号ID
	public static final String app_id = "wxec50dac5d04a0c9f";
	
	public static final String sapp_id = "wxbdf58228cad1de4f";
	
	// 商户号
	public static final String mch_id = "1512674051";
	// 小程序商户号
	public static final String smch_id = "1518310071";

	// 签名类型
	public static final String sign_type = "MD5";


	//终端ip
//	public static final String spbill_create_ip = "https://113.96.132.117:8443";
	//public static final String spbill_create_ip = "106.91.26.72";
//	public static final String spbill_create_ip = "39.98.34.125";
	public static final String spbill_create_ip = "192.168.43.243";
	//public static final String spbill_create_ip ="106.91.21.203";
	
    //通知地址1(购买油卡的回调地址)
	public static final String notify_url = "http://192.168.43.243:8899/verifyAsyn/verifyBuyPetrolInfoWeChat";
//	public static final String notify_url = SystemConstants.IPAPI+"/TinklingCat/api/verifyAsyn/orderPayNotify";
//	public static final String snotify_url = SystemConstants.IPAPI+"/TinklingCat/api/smallPay/payCallback";

	//交易类型
	public static final String trade_type = "APP";
	
	// 设备号
	public static final String device_info = "WEB";

	// 商品描述
	public static final String body = "人多多";

	// 请求预支付接口
	public static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}
