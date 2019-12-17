package com.cqut.czb.bn.entity.dto;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-22 14:06
 */
public class WCProgramConfig {
    //小程序 appId
    public static final String app_id = "wxd2d0171429ac208f";

    //小程序 appSecret
    public static final String app_secret = "f3bf5f0e40c5a76eb9e84c9f683bcf1c";

    //授权类型，此处只需填写 authorization_code
    public static final String grant_type = "authorization_code";

    // 微信小程序提现API_KEY
    public static final String WECHAT_WITHDRAW_API_KEY = "jD1qNX96o5KSPomZ4xv8cdelsMhz9nnL";

    // 微信小程序提现接口
    public static final String WECHAT_WITHDRAW_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    // 微信商户号(重庆人多多科技有限公司)
    public static final String mchid = "1566253131";

}