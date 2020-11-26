package com.cqut.czb.bn.entity.dto.withoutCard;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PetrolSalesWithoutDto extends PageDTO {
    private String recordId;

    private String petrolNum;

    private String userAccount;

    private String commodityTitle;

    private Integer paymentMethod;

    private String thirdOrderId;

    private Double turnoverAmount;

    private String transactionArea;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date transactionTime;

    private String petrolId;

    private Integer state;

    private Integer recordType;

    private Integer isRecharged;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum == null ? null : petrolNum.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId == null ? null : thirdOrderId.trim();
    }

    public Double getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(Double turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public String getTransactionArea() {
        return transactionArea;
    }

    public void setTransactionArea(String transactionArea) {
        this.transactionArea = transactionArea == null ? null : transactionArea.trim();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId == null ? null : petrolId.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getIsRecharged() {
        return isRecharged;
    }

    public void setIsRecharged(Integer isRecharged) {
        this.isRecharged = isRecharged;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
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
}
