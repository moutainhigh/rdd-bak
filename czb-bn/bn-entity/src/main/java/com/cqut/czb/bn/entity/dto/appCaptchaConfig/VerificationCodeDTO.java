package com.cqut.czb.bn.entity.dto.appCaptchaConfig;

import com.cqut.czb.bn.util.string.StringUtil;

public class VerificationCodeDTO {

    private String userPsw;

    private String verificationCodeId;

    private String userAccount;

    private String content;

    private int state;

    public VerificationCodeDTO(String userAccount , String content) {
        this.userAccount = userAccount;
        this.verificationCodeId= StringUtil.createId();
        this.content=getContent();
        this.state=0;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getVerificationCodeId() {
        return verificationCodeId;
    }

    public void setVerificationCodeId(String verificationCodeId) {
        this.verificationCodeId = verificationCodeId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
