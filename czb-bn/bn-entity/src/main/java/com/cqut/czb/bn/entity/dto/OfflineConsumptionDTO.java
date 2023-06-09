package com.cqut.czb.bn.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OfflineConsumptionDTO extends PageDTO{
    private String recordId;

    private String petrolNum;

    private double amount;

    private String state;

    private String account;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rechargeTime;

    private Integer isSpecial;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    @Override
    public String toString() {
        return "OfflineConsumptionDTO{" +
                "recordId='" + recordId + '\'' +
                ", petrolNum='" + petrolNum + '\'' +
                ", amount=" + amount +
                ", state='" + state + '\'' +
                ", account='" + account + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rechargeTime=" + rechargeTime +
                ", isSpecial=" + isSpecial +
                '}';
    }
}
