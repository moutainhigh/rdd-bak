package com.cqut.czb.bn.entity.dto.petrolRecharge;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PetrolRechargeOutputDTO {
    private String recordId;
    private String userName;
    private String userPhone;
    private String petrolNum;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseTime;
    private Date rechargeTime;
    private double petrolDenomination;
    private double petrolPrice;
    private String isRecharged;
    private String buyWay;
    private String petrolKind;
    private String recordType;
    private Double totalMoney;  //未/已充值总金额

    public String getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(String petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public double getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(double petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getIsRecharged() {
        return isRecharged;
    }

    public void setIsRecharged(String isRecharged) {
        this.isRecharged = isRecharged;
    }

    public String getBuyWay() {
        return buyWay;
    }

    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
