package com.cqut.czb.bn.entity.dto.integral;

import java.util.Date;

public class IntegralRechargeDTO {

    private String integralLogId;

    private String integralInfoId;

    private String userId;

    private Integer integralLogType;

    private Integer integralAmount;

    private Double amount;

    private String orderId;

    private Integer beforeIntegralAmount;

    private Integer rechargeWay;

    private String thirdOrderId;

    private String remark;

    private Integer isReceived;

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

    public Integer getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Integer isReceived) {
        this.isReceived = isReceived;
    }

    public Integer getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(Integer rechargeWay) {
        this.rechargeWay = rechargeWay;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }
}
