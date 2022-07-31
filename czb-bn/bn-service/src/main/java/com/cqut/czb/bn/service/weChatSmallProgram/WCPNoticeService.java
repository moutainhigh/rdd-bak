package com.cqut.czb.bn.service.weChatSmallProgram;

public interface WCPNoticeService {
    String pushOneUser(String openid, String templateid, String[] values, String first, String remark);

    /* * 获取access_token * appid和appsecret到小程序后台获取，当然也可以让小程序开发人员给你传过来 * */
    String getAccess_token();
}
