package com.cqut.czb.bn.entity.entity;

import com.cqut.czb.bn.entity.dto.PageDTO;

import java.util.Date;

public class VIPApply extends PageDTO {
    private String vipAccount;

    private String vipID;

    private String vipName;

    private Integer audit;

    private String phoneNum;

    /**
     * get方法
     * @return
     */
    public String getVipAccount() {
        return vipAccount;
    }

    public String getVipName() {
        return vipName;
    }

    public Integer getAudit() {
        return audit;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getVipID() {
        return vipID;
    }

    /**
     * set方法
     * @param vipAccount
     */
    public void setVipAccount(String vipAccount) {
        this.vipAccount = vipAccount;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setVipID(String vipID) {
        this.vipID = vipID;
    }
}
