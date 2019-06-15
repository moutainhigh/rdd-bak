package com.cqut.czb.bn.entity.dto.paymentRecord;

public class AiHuAlipayConfig {

	//APPID
	public static final  String app_id = "2018060160263534";

	//私钥
	public static final  String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCP4Rp7+xxFDf20wSNOhTxnLNANS8gYlgebWwjWT9ew17iLlg0vfTzonCbIeRIWgZFBbRdQnby5XaGA0NIhodoJhxGc724NOUZHXhAO8l/9nimMm6rUWm6360DNYktNqfoRXS4/I9zhE8W1UGeZIzWd23rkeHMy4VQbrBbBMN3snGeWOLDdABE07P84uGtN3mrIh5osX5wZxBQFotb6D0x2DfuzNnkDhVBLbZxFj+K8WAV75Bm48OAXcE16M5x5OraMcIGuABwYkq95qin8T3v1WteuNL7qGaGVX5jhsbCUM4xoOe7HdTL2yoIl6LYanxaU8VVFXy0XRnNgW5teeNylAgMBAAECggEAKaK1fE1FeAI9edIuOmm5+g1ww3g7lknMN7vXSdVfEHDMmbA5bpWqS5HsczT98/9YnntINO8Ajw+2TcWgGVVx+DHa+fQbf74kRUDGGt0uxADB+0uM2ti0k4qDfSvFqNrYX6/8Uw84uvVy0C5NXHZg7KZ34KeuhnnIUh2xlARlJDQLeJnvfgfoiZrGlyaXYbQTd3/++6kW0xz1Z6I3FjgthDolBwCCqqPZ5YwMV4fxf4adjGkldVNisFfnZTIdM7/LJIrHR7Oi44GlBalFqa8n8JY9ZpfMtvYV2xFHunShUcvrN7bt2096O2qQcz/ymUHk92gWG5z1A6Oaj1nnJEPY/QKBgQDDzOR0ljoiuWm+VDPMnaVxaN9iBXxHZtBE3EsRq5nyC1utKvhnX2gLSekeuMLVSFOBLShvZzCAGdi9m5YLx0CIaNvof3SzeJp2Orkf+6pI8i5NIIsRzUfca1wu21OpK/2JI+xkG5mo5MVQZdQCFVebi1bPmTSzhvluLOO1TW1VqwKBgQC8HZhSJQIE+kX7HqB5RDNzburvp+SGks1AGIrTdek95wJc/LbU13E8njIqDbw7o47ZVaYAfNq8yIVv3Pmsh/TSoLaMs+JmHLrB8QmWq+djQnrRyZf5uFbCprz0WRvLaewN098aMrsHCplNXvCfpgbcK0StjSHCg+oSwRNNBIum7wKBgHztgypcG7XCJZT5tp8sSr4kkrrBEz2ffO0ivYcFvVCxLIo9q4aRWhoxy1r9udpJrnXyMSV7anehhifcWI0lUah3dzBN+lD0d2FOMIYWldy7IQ224OS33Mznd0ayTBvZZIgW8qIg9hbL+JVGk1HM+jvVWMtWTkjpqsZAP/2S7FrHAoGASUiSPXYTWlMRjim5RGSwUojkV2alnVN1SEIHWye1LJthDU6Z/0LK512Nl1pweR9XmA1ItSnuoNrROOnyDieM1b0sQJYXXcZVbxZysNLZOo78f9u0K+GHUx9Xvy0zqcPi5OQkJvWabLzRShXxUZCBXaYxhBbWDwwK1TGITlm1+dsCgYEAhAAzYlzSzatewZaPnZv2sK72b0GajROO1WpLi/W+pbuaE45xPjAzslSQruv4uT7u6vPlHoKySj2IjdNGFwolxS5Lij+LOZgigbHMZwII8gAbYTUTOAEy9iOfqQGpAMCXkUwWhlJXzv2yyDS+xr/hoY9iO+GJmVkHMbtdflQAz0U=";

	//支付宝公钥
	public static final  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlq4Z3VHsHvGDq9KnNVk8hIGt4575TXLYAsVEq0AdgMkQiQ8RONXX7FmZq/8PdVoyRczO/cMgQ4dllcvUh+ydnTSQgUe1/CsdjyIhLAvPd0R3YreXNJFEUoYeczW0/7M7lgUr5mXiCVOSPxag9qED/sbH/qNU29vGJuzchPyQiOg3TBamj0vRwwo3bOJdL/vaPMFFJ1q74g47qhJM24GD7jADkf10Kzc3M3GNJ7TFa/3IGqQzsfdQPGYErM6gYzSSQTu+SvQsYxZoX+dgsrO8ErI7J3M/Um9tRhFwFqT6XR+LzNlLON4YtOOhXcgp5N78e4lHWWSq5fsYzrCMpuWJkQIDAQAB";

	//网关地址——支付网关
	public static final  String gatewayUrl = "https://openapi.alipay.com/gateway.do";

	//购买油卡的回调的url
	public static String notify_url ="http://"+UrlConfig.BACK_URL+":8899/verifyAsyn/verifyBuyPetrolInfoAiHu";
	//充值的回调的url
	public static String recharge_url="http://"+UrlConfig.BACK_URL+":8899/personCenter/VIP/purchaseVIP";
	//购买油卡充值url
	public static String PetrolRecharge_url="http://"+UrlConfig.BACK_URL+":8899/verifyAsyn/verifyPetrolRechargeInfoAiHu";

	// 加密方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "UTF-8";

	//参数返回格式
	public static String format = "json";
	
	//订单允许的最晚付款时间30分钟
	public static String timeout_express = "30m";
	
	//产品码
	public static String product_code = "QUICK_MSECURITY_PAY";
	
    //成功
	public static String response_success = "success";
	
	//失败
	public static String response_fail = "failure";

	public AiHuAlipayConfig() {
//		http://106.91.31.205:8899/verifyAsyn/verifyAsynNoticeInfoAiHu
	}
}
