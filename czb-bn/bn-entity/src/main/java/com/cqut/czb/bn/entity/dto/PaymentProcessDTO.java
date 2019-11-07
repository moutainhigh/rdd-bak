package com.cqut.czb.bn.entity.dto;

public class PaymentProcessDTO {

    private String thirdOrderId ;

    private String orgId;//商家订单

    // payType"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
    private String payType ;

    private double money ;

    private int petrolKind ;

    private String petrolNum ;

    private String ownerId;

    private String addressId ;

    private double actualPayment;

    private String area;//地区

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(int petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(double actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
