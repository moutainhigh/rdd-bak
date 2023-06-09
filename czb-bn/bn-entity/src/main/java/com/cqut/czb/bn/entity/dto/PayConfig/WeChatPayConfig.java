package com.cqut.czb.bn.entity.dto.PayConfig;

/**
 *
 * @author chendeqiang
 *
 */
public class WeChatPayConfig {

	// 密钥key（爱动）
	public static final String key = "CHONGQINGAiDonginformation201808";

	//小程序（人多多）
	public static final String skey="jD1qNX96o5KSPomZ4xv8cdelsMhz9nnL";

	// 公众账号ID
	public static final String app_id = "wxec50dac5d04a0c9f";

	// 人多多公众账号ID
	public static final String ren_duo_duo_app_id = "wx0a4273c49edc6e4a";

	//人多多小程序
	public static final String sapp_id = "wxd2d0171429ac208f";

	// app商户号
	public static final String mch_id = "1512674051";

	// 小程序商户号
	public static final String smch_id = "1566253131";

	// 签名类型
	public static final String sign_type = "MD5";

	//终端ip
	public static final String spbill_create_ip = UrlConfig.NOTIGY_URL;

    //通知地址1(购买油卡的回调地址)
	public static final String notify_url = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/verifyAsyn/verifyBuyPetrolInfoWeChat";

	//购买服务
	public static final String BuyService_url = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/verifyAsyn/verifyBuyServiceInfoWeChat";

	//充值vip
	public static final String RechargeVip_url = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/verifyAsyn/verifyRechargeVipInfoWeChat";

	//洗车
	public static final String BuyCarWash_url = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/verifyAsyn/verifyBuyCarWashInfoWeChat";

	//点餐
	public static final String BuyDish_url = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/verifyAsyn/verifyBuyDishInfoWeChat";

	//直充系统
	public static final String Direct_url = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/WechatPayReturn/wechatPayRetur";

	public static final String applet_url="http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/AppletVerifyAsyn/verifyAppletWeChat";

	//小程序库存支付地址
	public static final String applet_url2 = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/AppletVerifyAsyn/verifyAppletPaymentWeChat";

	//小程序Vip支付地址
	public static final String applet_url3 = "http://"+ UrlConfig.NOTIGY_URL+":"+UrlConfig.Port+"/AppletVerifyAsyn/verifyAppletRechargeVip";
	//交易类型——app支付
	public static final String trade_type = "APP";

	//微信小程序支付
	public static final String WeChat_applet_trade_type = "JSAPI";

	// 设备号
	public static final String device_info = "WEB";

	// 商品描述
	public static final String body = "代充值服务";

	// 请求预支付接口
	public static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}
