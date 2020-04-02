package com.cqut.czb.bn.entity.dto.automaticRecharge;


import io.swagger.models.auth.In;

public class SumAutoRecharge {
    private Integer successPeople;
    private Integer failedPeople;
    private Integer sumPeople;
    private double sumRechargeAmount;
    private double sumPlace;
    private String startTime;
    private String endTime;

    public Integer getSuccessPeople() {
        return successPeople;
    }

    public void setSuccessPeople(Integer successPeople) {
        this.successPeople = successPeople;
    }

    public Integer getFailedPeople() {
        return failedPeople;
    }

    public void setFailedPeople(Integer failedPeople) {
        this.failedPeople = failedPeople;
    }

    public Integer getSumPeople() {
        return sumPeople;
    }

    public void setSumPeople(Integer sumPeople) {
        this.sumPeople = sumPeople;
    }

    public double getSumRechargeAmount() {
        return sumRechargeAmount;
    }

    public void setSumRechargeAmount(double sumRechargeAmount) {
        this.sumRechargeAmount = sumRechargeAmount;
    }

    public double getSumPlace() {
        return sumPlace;
    }

    public void setSumPlace(double sumPlace) {
        this.sumPlace = sumPlace;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
