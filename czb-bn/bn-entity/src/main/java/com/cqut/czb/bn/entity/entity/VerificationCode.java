package com.cqut.czb.bn.entity.entity;

import com.cqut.czb.bn.util.string.StringUtil;

import java.util.Random;

public class VerificationCode {

    private String userPsw;

    private String verificationCodeId;

    private String userAccount;

    private String content;

    private int state;

    public VerificationCode(String userAccount) {
        this.userAccount = userAccount;
        this.verificationCodeId= StringUtil.createId();
        this.content=creatVerificationCode();
        this.state=0;
    }

    /**
     * 自动生成验证码
     * @return
     */
    public String  creatVerificationCode(){
        Random random = new Random();
        int randomNum = random.nextInt(1000000);
        String randomCode = String.format("%06d", randomNum);
        return randomCode;
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
