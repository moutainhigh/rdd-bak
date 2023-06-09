package com.cqut.czb.bn.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OfflineClientDTO extends PageDTO{

    private String account;

    private double balance;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime;

    private double totalConsumption;

    private double totalRecharge;

    private double totalTurn;

    private Integer isSpecial;

    public double getTotalTurn() {
        return totalTurn;
    }

    public void setTotalTurn(double totalTurn) {
        this.totalTurn = totalTurn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(double totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    @Override
    public String toString() {
        return "OfflineClientDTO{" +
                "account='" + account + '\'' +
                ", balance=" + balance +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", registerTime=" + registerTime +
                ", totalConsumption=" + totalConsumption +
                ", totalRecharge=" + totalRecharge +
                ", totalTurn=" + totalTurn +
                ", isSpecial=" + isSpecial +
                '}';
    }
}
