package com.cqut.czb.bn.entity.dto.servicePlan;

import javax.validation.constraints.NotNull;

public class ServicePlanInputDTO {

    private String planId;

    @NotNull(message = "套餐名不能为null")
    private String planName;

    private Double planAmount;

    private Integer planTime;

    private Double rentBackMoney;

    private Double payMoney;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(Double planAmount) {
        this.planAmount = planAmount;
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
}
