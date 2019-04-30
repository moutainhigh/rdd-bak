package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class UserIncomeInfo {
    private String infoId;

    private String userId;

    private Double fanyongIncome;

    private Double shareIncome;

    private Double payTotalRent;

    private Double gotTotalRent;

    private Double otherIncome;

    private Date createAt;

    private Date updateAt;

    private Double withdrawed;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId == null ? null : infoId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Double getFanyongIncome() {
        return fanyongIncome;
    }

    public void setFanyongIncome(Double fanyongIncome) {
        this.fanyongIncome = fanyongIncome;
    }

    public Double getShareIncome() {
        return shareIncome;
    }

    public void setShareIncome(Double shareIncome) {
        this.shareIncome = shareIncome;
    }

    public Double getPayTotalRent() {
        return payTotalRent;
    }

    public void setPayTotalRent(Double payTotalRent) {
        this.payTotalRent = payTotalRent;
    }

    public Double getGotTotalRent() {
        return gotTotalRent;
    }

    public void setGotTotalRent(Double gotTotalRent) {
        this.gotTotalRent = gotTotalRent;
    }

    public Double getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Double otherIncome) {
        this.otherIncome = otherIncome;
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

    public Double getWithdrawed() {
        return withdrawed;
    }

    public void setWithdrawed(Double withdrawed) {
        this.withdrawed = withdrawed;
    }
}