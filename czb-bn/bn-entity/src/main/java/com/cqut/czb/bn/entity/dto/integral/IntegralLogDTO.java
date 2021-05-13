package com.cqut.czb.bn.entity.dto.integral;

import java.util.Date;

public class IntegralLogDTO {
    private String integralLogId;

    private String integralInfoId;

    private String userId;

    private Integer integralLogType;

    private Integer integralAmount;

    private String orderId;

    private Integer beforeIntegralAmount;

    private Double amount;

    private String remark;

    private String userAccount;

    private Date createAt;

    private Date updateAt;

    public String getIntegralLogId() {
        return integralLogId;
    }

    public void setIntegralLogId(String integralLogId) {
        this.integralLogId = integralLogId == null ? null : integralLogId.trim();
    }

    public String getIntegralInfoId() {
        return integralInfoId;
    }

    public void setIntegralInfoId(String integralInfoId) {
        this.integralInfoId = integralInfoId == null ? null : integralInfoId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getIntegralLogType() {
        return integralLogType;
    }

    public void setIntegralLogType(Integer integralLogType) {
        this.integralLogType = integralLogType;
    }

    public Integer getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(Integer integralAmount) {
        this.integralAmount = integralAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getBeforeIntegralAmount() {
        return beforeIntegralAmount;
    }

    public void setBeforeIntegralAmount(Integer beforeIntegralAmount) {
        this.beforeIntegralAmount = beforeIntegralAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
