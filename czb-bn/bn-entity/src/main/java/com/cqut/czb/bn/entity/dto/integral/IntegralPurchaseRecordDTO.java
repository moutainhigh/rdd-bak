package com.cqut.czb.bn.entity.dto.integral;

import java.util.Date;

public class IntegralPurchaseRecordDTO {
    private String integralPurchaseRecordId;

    private String userId;

    private String userAccount;

    private Double amount;

    private Integer rechargeWay;

    private Integer isReceived;

    private Integer integralAmount;

    private String thirdTradeNum;

    private Date createAt;

    private Date updateAt;

    private String beginDate;

    private String overDate;

    public String getIntegralPurchaseRecordId() {
        return integralPurchaseRecordId;
    }

    public void setIntegralPurchaseRecordId(String integralPurchaseRecordId) {
        this.integralPurchaseRecordId = integralPurchaseRecordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(Integer rechargeWay) {
        this.rechargeWay = rechargeWay;
    }

    public Integer getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Integer isReceived) {
        this.isReceived = isReceived;
    }

    public Integer getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(Integer integralAmount) {
        this.integralAmount = integralAmount;
    }

    public String getThirdTradeNum() {
        return thirdTradeNum;
    }

    public void setThirdTradeNum(String thirdTradeNum) {
        this.thirdTradeNum = thirdTradeNum;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getOverDate() {
        return overDate;
    }

    public void setOverDate(String overDate) {
        this.overDate = overDate;
    }
}
