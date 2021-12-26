package com.cqut.czb.bn.entity.dto.directCustomers;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DirectCustomerManagerDto {
    private int state;
    private int type;
    private String orderId;
    private String userId;
    private String account;
    private String role;
    private String rechargeAccount;
    private double rechargeAmount;
    private double balance;
    private double consumptionAmount;
    private double amountRecovered;
    private String oldPwd;
    private String newPwd;
    private double totalRecharge;
    private double totalRecovered;
    private Integer isRecharged;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getIsRecharged() {
        return isRecharged;
    }

    public void setIsRecharged(Integer isRecharged) {
        this.isRecharged = isRecharged;
    }

    public double getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(double totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public double getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(double totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRechargeAccount() {
        return rechargeAccount;
    }

    public void setRechargeAccount(String rechargeAccount) {
        this.rechargeAccount = rechargeAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime;

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(double consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public double getAmountRecovered() {
        return amountRecovered;
    }

    public void setAmountRecovered(double amountRecovered) {
        this.amountRecovered = amountRecovered;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "DirectCustomerManagerDto{" +
                "state=" + state +
                ", type=" + type +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", role='" + role + '\'' +
                ", rechargeAccount='" + rechargeAccount + '\'' +
                ", rechargeAmount=" + rechargeAmount +
                ", balance=" + balance +
                ", consumptionAmount=" + consumptionAmount +
                ", amountRecovered=" + amountRecovered +
                ", oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", totalRecharge=" + totalRecharge +
                ", totalRecovered=" + totalRecovered +
                ", isRecharged=" + isRecharged +
                ", registerTime=" + registerTime +
                '}';
    }
}
