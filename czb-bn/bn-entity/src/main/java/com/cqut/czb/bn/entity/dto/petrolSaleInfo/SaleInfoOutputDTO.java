package com.cqut.czb.bn.entity.dto.petrolSaleInfo;

import java.util.Date;

public class SaleInfoOutputDTO {
    private String petrolNum;//油卡号码
    private String petrolKind;//油卡种类 0 国通，1 中石油，2 中石化
    private String petrolDenomination;//油卡面额
    private String petrolPrice;//油卡价格
    private String owner;//购买者
    private String paymentMethod;//支付方式
    private Date transactionTime;//交易时间
    private String area;//地区
    private String turnoverAmount;

    public String getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(String turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(String petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(String petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
