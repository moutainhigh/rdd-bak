package com.cqut.czb.bn.entity.entity.withDrawed;

public class Income {
    private String account;

    private Double allAmount;

    private Double balance;

    private Double withdrawed;

    private Integer withdrawedAmount;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(Double allAmount) {
        this.allAmount = allAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getWithdrawed() {
        return withdrawed;
    }

    public void setWithdrawed(Double withdrawed) {
        this.withdrawed = withdrawed;
    }

    public Integer getWithdrawedAmount() {
        return withdrawedAmount;
    }

    public void setWithdrawedAmount(Integer withdrawedAmount) {
        this.withdrawedAmount = withdrawedAmount;
    }
}
