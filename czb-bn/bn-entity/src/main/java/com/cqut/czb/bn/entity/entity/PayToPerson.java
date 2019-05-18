package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class PayToPerson {
    private String recordId;

    private String payeeName;

    private String payeeIdCard;

    private String contractRecordId;

    private Double payableMoney;

    private Double actualPayMoney;

    private Date targetYearMonth;

    private Date platformPayTime;

    private Integer state;

    private Integer isDeleted;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName == null ? null : payeeName.trim();
    }

    public String getPayeeIdCard() {
        return payeeIdCard;
    }

    public void setPayeeIdCard(String payeeIdCard) {
        this.payeeIdCard = payeeIdCard == null ? null : payeeIdCard.trim();
    }

    public String getContractRecordId() {
        return contractRecordId;
    }

    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId == null ? null : contractRecordId.trim();
    }

    public Double getPayableMoney() {
        return payableMoney;
    }

    public void setPayableMoney(Double payableMoney) {
        this.payableMoney = payableMoney;
    }

    public Double getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(Double actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    public Date getTargetYearMonth() {
        return targetYearMonth;
    }

    public void setTargetYearMonth(Date targetYearMonth) {
        this.targetYearMonth = targetYearMonth;
    }

    public Date getPlatformPayTime() {
        return platformPayTime;
    }

    public void setPlatformPayTime(Date platformPayTime) {
        this.platformPayTime = platformPayTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}