package com.cqut.czb.bn.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OfflineConsumptionDTO extends PageDTO{

    private String petrolNum;

    private double amount;

    private int state;

    private String account;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rechargeTime;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
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
}
