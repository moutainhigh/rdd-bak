package com.cqut.czb.bn.entity.dto.PayConfig;

public class WeChatH5PayConfig {

    //人多多
    public static final String key="jD1qNX96o5KSPomZ4xv8cdelsMhz9nnL";

    // 账号ID
    public static final String app_id = "ww537eb30a09eaa286";

    // 商户号
    public static final String mch_id = "1566253131";

    // 签名类型
    public static final String sign_type = "MD5";

    //终端ip
    public static final String spbill_create_ip = UrlConfig.NOTIGY_URL;

    //直充系统
    public static final String Direct_url = "http://" + UrlConfig.Domain_name + ":"+UrlConfig.Port+"/WechatPayReturn/wechatPayReturn";

    //交易类型——h5支付
    public static final String trade_type = "MWEB";

    // 设备号
//    public static final String device_info = "WEB";

    // 商品描述
    public static final String body = "代充值服务";

    // 请求预支付接口
    public static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
