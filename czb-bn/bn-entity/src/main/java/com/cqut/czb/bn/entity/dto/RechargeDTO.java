package com.cqut.czb.bn.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RechargeDTO {
    private String recordId;

    private String infoId;

    private String userId;

    private int type;

    private String balance;

    private String account;

    private double rechargeAmount;

    public String getRecordId() {
        return recordId;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }
}
