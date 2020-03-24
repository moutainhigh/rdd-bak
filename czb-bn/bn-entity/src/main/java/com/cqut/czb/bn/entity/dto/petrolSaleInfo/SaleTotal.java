package com.cqut.czb.bn.entity.dto.petrolSaleInfo;

public class SaleTotal {
    private double fristTotal; // 首充合计
    private int fristTotalNumber;  // 首充人数
    private double continueTotal;  // 续充合计
    private int continueTotalNumber;  // 续充人数
    private double vipRecordTotal;   // 会员费统计
    private int vipRecordTotalNumber;  // 会员人数
    private double total;   // 总计

    private double noFristTotal; // 首充未充值合计
    private int noFristTotalNumber;  // 首充人数
    private double yFristTotal; // 首充已充值合计
    private int yFristTotalNumber;  // 首充人数

    private int isVip;

    public SaleTotal() {
        this(0.0,0,0.0,0,0.0,0,0.0,0.0,0.0);
    }

    public SaleTotal(double fristTotal, int fristTotalNumber, double continueTotal, int continueTotalNumber, double vipRecordTotal, int vipRecordTotalNumber, double total, double noFristTotal, double yFristTotal) {
        this.fristTotal = fristTotal;
        this.fristTotalNumber = fristTotalNumber;
        this.continueTotal = continueTotal;
        this.continueTotalNumber = continueTotalNumber;
        this.vipRecordTotal = vipRecordTotal;
        this.vipRecordTotalNumber = vipRecordTotalNumber;
        this.total = total;
        this.noFristTotal = noFristTotal;
        this.yFristTotal = yFristTotal;
    }

    public double getFristTotal() {
        return fristTotal;
    }

    public void setFristTotal(double fristTotal) {
        this.fristTotal = fristTotal;
    }

    public double getContinueTotal() {
        return continueTotal;
    }

    public void setContinueTotal(double continueTotal) {
        this.continueTotal = continueTotal;
    }

    public double getVipRecordTotal() {
        return vipRecordTotal;
    }

    public void setVipRecordTotal(double vipRecordTotal) {
        this.vipRecordTotal = vipRecordTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getNoFristTotal() {
        return noFristTotal;
    }

    public void setNoFristTotal(double noFristTotal) {
        this.noFristTotal = noFristTotal;
    }

    public double getyFristTotal() {
        return yFristTotal;
    }

    public void setyFristTotal(double yFristTotal) {
        this.yFristTotal = yFristTotal;
    }

    public int getFristTotalNumber() {
        return fristTotalNumber;
    }

    public void setFristTotalNumber(int fristTotalNumber) {
        this.fristTotalNumber = fristTotalNumber;
    }

    public int getContinueTotalNumber() {
        return continueTotalNumber;
    }

    public void setContinueTotalNumber(int continueTotalNumber) {
        this.continueTotalNumber = continueTotalNumber;
    }

    public int getVipRecordTotalNumber() {
        return vipRecordTotalNumber;
    }

    public void setVipRecordTotalNumber(int vipRecordTotalNumber) {
        this.vipRecordTotalNumber = vipRecordTotalNumber;
    }

    public int getNoFristTotalNumber() {
        return noFristTotalNumber;
    }

    public void setNoFristTotalNumber(int noFristTotalNumber) {
        this.noFristTotalNumber = noFristTotalNumber;
    }

    public int getyFristTotalNumber() {
        return yFristTotalNumber;
    }

    public void setyFristTotalNumber(int yFristTotalNumber) {
        this.yFristTotalNumber = yFristTotalNumber;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }
}
