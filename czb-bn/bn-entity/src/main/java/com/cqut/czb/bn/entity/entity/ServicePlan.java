package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class ServicePlan {
    private String planId;

    private String planName;

    private Double planAmoount;

    private Integer planTime;

    private Double rentBackMoney;

    private Double payMoney;

    private Date ctreateAt;

    private Date updateAt;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Double getPlanAmoount() {
        return planAmoount;
    }

    public void setPlanAmoount(Double planAmoount) {
        this.planAmoount = planAmoount;
    }

    public Integer getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Integer planTime) {
        this.planTime = planTime;
    }

    public Double getRentBackMoney() {
        return rentBackMoney;
    }

    public void setRentBackMoney(Double rentBackMoney) {
        this.rentBackMoney = rentBackMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Date getCtreateAt() {
        return ctreateAt;
    }

    public void setCtreateAt(Date ctreateAt) {
        this.ctreateAt = ctreateAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}