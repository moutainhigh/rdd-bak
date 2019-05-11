package com.cqut.czb.bn.entity.dto.appPersonalCenter;

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
