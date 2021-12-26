package com.cqut.czb.bn.entity.dto.directCustomers;

public class CustomerBalanceDto {
    private double rechargeAmount;
    private double balance;
    private double consumptionAmount;

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
}
