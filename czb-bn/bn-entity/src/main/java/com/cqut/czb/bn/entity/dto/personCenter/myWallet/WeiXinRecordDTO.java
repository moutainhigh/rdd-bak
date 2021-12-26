package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

import java.math.BigDecimal;

public class WeiXinRecordDTO {
    //微信用户号
    private String wxAccount;
    //账号密码
    private String keyword;
    //用户姓名
    private String wxName;
    //提现金额
    private Double amount;

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
