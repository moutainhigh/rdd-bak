package com.cqut.czb.bn.entity.dto.appPersonalCenter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MyIncomeLogDTO {

    private String userId;

    private Double amount;

    private Double beforeChangeIncome;

    private String remark;

    private Date createAt;

    private Date updateAt;

    private String withdrawTo;

    private String withdrawName;

    private Double withdrawAccount;

    private Integer type;

    private String userName;

    private String incomeClass;

    private Integer commissionLevel;

    private String commissionSourceUser;

    private String commissionGotUser;

    public String getCommissionGotUser() {
        return commissionGotUser;
    }

    public void setCommissionGotUser(String commissionGotUser) {
        this.commissionGotUser = commissionGotUser;
    }

    public String getCommissionSourceUser() {
        return commissionSourceUser;
    }

    public void setCommissionSourceUser(String commissionSourceUser) {
        this.commissionSourceUser = commissionSourceUser;
    }

    public Integer getCommissionLevel() {
        return commissionLevel;
    }

    public void setCommissionLevel(Integer commissionLevel) {
        this.commissionLevel = commissionLevel;
    }

    public String getUserName() {
        return userName;
    }

    public String getIncomeClass() {
        return incomeClass;
    }

    public void setIncomeClass(String incomeClass) {
        this.incomeClass = incomeClass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
        this.remark = remark;
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
        this.withdrawTo = withdrawTo;
    }

    public String getWithdrawName() {
        return withdrawName;
    }

    public void setWithdrawName(String withdrawName) {
        this.withdrawName = withdrawName;
    }

    public Double getWithdrawAccount() {
        return withdrawAccount;
    }

    public void setWithdrawAccount(Double withdrawAccount) {
        this.withdrawAccount = withdrawAccount;
    }
}
