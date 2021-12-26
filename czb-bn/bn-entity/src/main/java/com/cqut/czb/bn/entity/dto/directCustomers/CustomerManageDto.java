package com.cqut.czb.bn.entity.dto.directCustomers;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerManageDto extends PageDTO {
    private String customerId;
    private String appId;
    private String appSecret;
    private String customerNumber;
    private double rechargeAmount;
    private double balance;
    private double consumptionAmount;
    private double amountRecovered;
    private String createAt;
    private String customerName;
    private int isShutRecharge;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    private Boolean automaticSubmit;

    public Boolean getAutomaticSubmit() {
        return automaticSubmit;
    }

    public void setAutomaticSubmit(Boolean automaticSubmit) {
        this.automaticSubmit = automaticSubmit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public int getIsShutRecharge() {
        return isShutRecharge;
    }

    public void setIsShutRecharge(int isShutRecharge) {
        this.isShutRecharge = isShutRecharge;
    }

    @Override
    public String toString() {
        return "CustomerManageDto{" +
                "customerId='" + customerId + '\'' +
                ", appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", rechargeAmount=" + rechargeAmount +
                ", balance=" + balance +
                ", consumptionAmount=" + consumptionAmount +
                ", amountRecovered=" + amountRecovered +
                ", createAt='" + createAt + '\'' +
                ", customerName='" + customerName + '\'' +
                ", isShutRecharge=" + isShutRecharge +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", automaticSubmit=" + automaticSubmit +
                '}';
    }
}
