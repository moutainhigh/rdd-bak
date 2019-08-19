package com.cqut.czb.bn.entity.dto.appPersonalCenter;

import java.util.Date;

/**
 * 创建人：陈德强
 * 创建时间：21019/4/22
 * 作用：个人收入信息
 */
public class UserIncomeInfoDTO {

    private String infoId;

    private String userId;

    private Double fanyongIncome;

    private Double shareIncome;

    private Double payTotalRent;

    private Double gotTotalRent;

    private Double otherIncome;

    private Double totalIncome;//总收益

    private Double blance;//余额

    private Double withdrawed;

    private Double refundMoney; //退还金额

    private Date updateAt;

    public Double getWithdrawed() {
        return withdrawed;
    }

    public void setWithdrawed(Double withdrawed) {
        this.withdrawed = withdrawed;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getBlance() {
        return blance;
    }

    public void setBlance(Double blance) {
        this.blance = blance;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
