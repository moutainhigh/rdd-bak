package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class TotalConsumptionDto {
    // 充值金额
    private double realPrice;

    // 支付额度
    private double rechargeAmount;

    // 订单量
    private int orderNumber;

    // 今日充值金额
    private double todayRealPrice;

    // 今日支付额度
    private double todayRechargeAmount;

    // 今日订单量
    private int todayOrderNumber;

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTodayRealPrice() {
        return todayRealPrice;
    }

    public void setTodayRealPrice(double todayRealPrice) {
        this.todayRealPrice = todayRealPrice;
    }

    public double getTodayRechargeAmount() {
        return todayRechargeAmount;
    }

    public void setTodayRechargeAmount(double todayRechargeAmount) {
        this.todayRechargeAmount = todayRechargeAmount;
    }

    public int getTodayOrderNumber() {
        return todayOrderNumber;
    }

    public void setTodayOrderNumber(int todayOrderNumber) {
        this.todayOrderNumber = todayOrderNumber;
    }
}
