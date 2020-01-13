package com.cqut.czb.bn.entity.entity;

import com.cqut.czb.bn.entity.dto.PageDTO;

public class VIPApply extends PageDTO {
    private String vipAccount;

    private String vipId;

    private String vipName;

    private Integer audit;

    private String phoneNum;

    private Integer isVip;

    private String createAt;

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

    public Integer getIsVip() {
        return isVip;
    }

    public String getVipId() {
        return vipId;
    }

    public String getCreateAt() {
        return createAt;
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

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }
    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
