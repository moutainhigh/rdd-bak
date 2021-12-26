package com.cqut.czb.bn.entity.dto.directCustomers;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;

public class ElectricityRechargeDto extends PageDTO {
    private String orderId;
    private String userId;
    private String regional;
    private String rechargeAccount;
    private String account;
    private double realAmount;
    private double rechargeAmount;
    private String state;
    private String thirdAccount;
    private double balance;

    //批量修改使用
    private String[] orders;

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date viewTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRechargeAccount() {
        return rechargeAccount;
    }

    public void setRechargeAccount(String rechargeAccount) {
        this.rechargeAccount = rechargeAccount;
    }

    public double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(double realAmount) {
        this.realAmount = realAmount;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
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

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    @Override
    public String toString() {
        return "ElectricityRechargeDto{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", regional='" + regional + '\'' +
                ", rechargeAccount='" + rechargeAccount + '\'' +
                ", account='" + account + '\'' +
                ", realAmount=" + realAmount +
                ", rechargeAmount=" + rechargeAmount +
                ", state='" + state + '\'' +
                ", thirdAccount='" + thirdAccount + '\'' +
                ", balance=" + balance +
                ", orders=" + Arrays.toString(orders) +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", viewTime=" + viewTime +
                '}';
    }
}
