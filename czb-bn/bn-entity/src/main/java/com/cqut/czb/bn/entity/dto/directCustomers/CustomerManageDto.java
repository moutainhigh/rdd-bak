package com.cqut.czb.bn.entity.dto.directCustomers;

import com.cqut.czb.bn.entity.dto.PageDTO;

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
}
