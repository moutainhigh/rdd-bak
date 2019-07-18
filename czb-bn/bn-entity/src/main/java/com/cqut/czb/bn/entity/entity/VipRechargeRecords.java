package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class VipRechargeRecords {
    private String recordId;

    private String userId;

    private Double amount;

    private Integer rechargeWay;

    private Integer isReceived;

    private String thirdTradeNum;

    private Date createAt;

    private Date updateAt;

    private String vipAreaConfigId;

    private Date rechargeTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public String getThirdTradeNum() {
        return thirdTradeNum;
    }

    public void setThirdTradeNum(String thirdTradeNum) {
        this.thirdTradeNum = thirdTradeNum == null ? null : thirdTradeNum.trim();
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

    public String getVipAreaConfigId() {
        return vipAreaConfigId;
    }

    public void setVipAreaConfigId(String vipAreaConfigId) {
        this.vipAreaConfigId = vipAreaConfigId == null ? null : vipAreaConfigId.trim();
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }
}