package com.cqut.czb.bn.entity.dto.PayConfig;

public class WeChatH5PayConfig {

    //人多多
    public static final String key="jD1qNX96o5KSPomZ4xv8cdelsMhz9nnL";

    // 账号ID
    public static final String app_id = "ww537eb30a09eaa286";

    public static final String public_app_id = "wx0a4273c49edc6e4a";

    // 商户号
    public static final String mch_id = "1566253131";

    // 签名类型
    public static final String sign_type = "MD5";

    //终端ip
    public static final String spbill_create_ip = UrlConfig.NOTIGY_URL;

    //直充系统
    public static final String Direct_url = "http://" + UrlConfig.Domain_name + ":"+UrlConfig.Port+"/WechatPayReturn/wechatPayReturn";

    //水电费充值
    public static final String Electricity_url = "http://" + UrlConfig.Domain_name + ":"+UrlConfig.Port+"/WechatPayReturn/WeChatPayBackElectricity";

    //购买积分
    public static final String Integral_url = "http://" + UrlConfig.Domain_name + ":"+UrlConfig.Port+"/WeChatPayment/weChatBuyIntegral";

    //中石化码商、无卡加油
    public static final String Applet_url = "http://" + UrlConfig.Domain_name + ":"+UrlConfig.Port+"/WeChatPayment/verifyAppletPaymentWeChat";

    //权益商品购买
    public static final String EquityGoods_url = "http://" + UrlConfig.Domain_name + ":"+UrlConfig.Port+"/WeChatPayment/weChatBuyEquityGoods";

    //交易类型——h5支付
    public static final String trade_type = "MWEB";

    // 设备号
//    public static final String device_info = "WEB";

    // 商品描述
    public static final String body = "充值服务";

    // 请求预支付接口
    public static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //微信提现接口
    public static final String wallet = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
}
