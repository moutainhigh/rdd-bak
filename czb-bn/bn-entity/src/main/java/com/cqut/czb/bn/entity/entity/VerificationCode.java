package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class VerificationCode {
    private String verificationCodeId;

    private String userAccount;

    private String content;

    private Date createAt;

    private Date updateAt;

    private Byte state;

    public String getVerificationCodeId() {
        return verificationCodeId;
    }

    public void setVerificationCodeId(String verificationCodeId) {
        this.verificationCodeId = verificationCodeId == null ? null : verificationCodeId.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}