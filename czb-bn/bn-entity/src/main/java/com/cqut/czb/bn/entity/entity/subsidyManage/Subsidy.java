package com.cqut.czb.bn.entity.entity.subsidyManage;

/**
 * auth:谭深化
 * time:2019-07-12
 */

public class Subsidy {
    // 补贴记录id
    private String subsidyId;

    // 账号
    private String account;

    // 补贴月份
    private String subsidyMonth;

    // 补贴比列
    private Double subsidyRate;

    // 补贴时间
    private String subsidyTime;

    // 当月收益
    private Double currentMonthProfit;

    // 补贴金额
    private Double subsidyMoney;

    public Subsidy() {

    }

    public String getSubsidyId() {
        return subsidyId;
    }

    public void setSubsidyId(String subsidyId) {
        this.subsidyId = subsidyId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSubsidyMonth() {
        return subsidyMonth;
    }

    public void setSubsidyMonth(String subsidyMonth) {
        this.subsidyMonth = subsidyMonth;
    }

    public Double getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(Double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public String getSubsidyTime() {
        return subsidyTime;
    }

    public void setSubsidyTime(String subsidyTime) {
        this.subsidyTime = subsidyTime;
    }

    public Double getCurrentMonthProfit() {
        return currentMonthProfit;
    }

    public void setCurrentMonthProfit(Double currentMonthProfit) {
        this.currentMonthProfit = currentMonthProfit;
    }

    public Double getSubsidyMoney() {
        return subsidyMoney;
    }

    public void setSubsidyMoney(Double subsidyMoney) {
        this.subsidyMoney = subsidyMoney;
    }
}
