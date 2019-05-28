package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class PlatformIncomeRecords {
    private String recordId;

    private String contractRecordId;

    private Double receivableMoney;

    private Double actualReceiptsMoney;

    private Date targetYearMonth;

    private Date enterprisePayTime;

    private Integer state;

    private Integer isDeleted;

    private Integer isNeedRecharge;

    private Integer isDistributed;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getContractRecordId() {
        return contractRecordId;
    }

    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId == null ? null : contractRecordId.trim();
    }

    public Double getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(Double receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public Double getActualReceiptsMoney() {
        return actualReceiptsMoney;
    }

    public void setActualReceiptsMoney(Double actualReceiptsMoney) {
        this.actualReceiptsMoney = actualReceiptsMoney;
    }

    public Date getTargetYearMonth() {
        return targetYearMonth;
    }

    public void setTargetYearMonth(Date targetYearMonth) {
        this.targetYearMonth = targetYearMonth;
    }

    public Date getEnterprisePayTime() {
        return enterprisePayTime;
    }

    public void setEnterprisePayTime(Date enterprisePayTime) {
        this.enterprisePayTime = enterprisePayTime;
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

    public Integer getIsNeedRecharge() {
        return isNeedRecharge;
    }

    public void setIsNeedRecharge(Integer isNeedRecharge) {
        this.isNeedRecharge = isNeedRecharge;
    }

    public Integer getIsDistributed() {
        return isDistributed;
    }

    public void setIsDistributed(Integer isDistributed) {
        this.isDistributed = isDistributed;
    }
}