package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class IncomeLog {
    private String recordId;

    private Double amount;

    private Integer type;

    private String infoId;

    private Double beforeChangeIncome;

    private String remark;

    private String souseId;

    private Date createAt;

    private Date updateAt;

    private String withdrawTo;

    private String withdrawName;

    private Double withdrawAccount;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId == null ? null : infoId.trim();
    }

    public Double getBeforeChangeIncome() {
        return beforeChangeIncome;
    }

    public void setBeforeChangeIncome(Double beforeChangeIncome) {
        this.beforeChangeIncome = beforeChangeIncome;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSouseId() {
        return souseId;
    }

    public void setSouseId(String souseId) {
        this.souseId = souseId == null ? null : souseId.trim();
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

    public String getWithdrawTo() {
        return withdrawTo;
    }

    public void setWithdrawTo(String withdrawTo) {
        this.withdrawTo = withdrawTo == null ? null : withdrawTo.trim();
    }

    public String getWithdrawName() {
        return withdrawName;
    }

    public void setWithdrawName(String withdrawName) {
        this.withdrawName = withdrawName == null ? null : withdrawName.trim();
    }

    public Double getWithdrawAccount() {
        return withdrawAccount;
    }

    public void setWithdrawAccount(Double withdrawAccount) {
        this.withdrawAccount = withdrawAccount;
    }
}