package com.cqut.czb.bn.entity.entity.petrolCoupons;

import java.util.Date;

public class PetrolCouponsSalesRecords {
    private String recordId;

    private String petrolId;

    private String buyerId;

    private String userAccount;

    private Integer paymentMethod;

    private String toRddOutTradeNo;

    private String toRddThirdOrderId;

    private Double toRddTurnoverAmount;

    private Date toRddTransactionTime;

    private Integer toRddState;

    private Double unitPrice;

    private Date toLuPayStartTime;

    private Date toLuPayEndTime;

    private Integer toLuPayState;

    private String toRddOutId;

    private String returnOrderId;

    private String tradingId;

    private String orderId;

    private String orderInfo;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId == null ? null : petrolId.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getToRddOutTradeNo() {
        return toRddOutTradeNo;
    }

    public void setToRddOutTradeNo(String toRddOutTradeNo) {
        this.toRddOutTradeNo = toRddOutTradeNo == null ? null : toRddOutTradeNo.trim();
    }

    public String getToRddThirdOrderId() {
        return toRddThirdOrderId;
    }

    public void setToRddThirdOrderId(String toRddThirdOrderId) {
        this.toRddThirdOrderId = toRddThirdOrderId == null ? null : toRddThirdOrderId.trim();
    }

    public Double getToRddTurnoverAmount() {
        return toRddTurnoverAmount;
    }

    public void setToRddTurnoverAmount(Double toRddTurnoverAmount) {
        this.toRddTurnoverAmount = toRddTurnoverAmount;
    }

    public Date getToRddTransactionTime() {
        return toRddTransactionTime;
    }

    public void setToRddTransactionTime(Date toRddTransactionTime) {
        this.toRddTransactionTime = toRddTransactionTime;
    }

    public Integer getToRddState() {
        return toRddState;
    }

    public void setToRddState(Integer toRddState) {
        this.toRddState = toRddState;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getToLuPayStartTime() {
        return toLuPayStartTime;
    }

    public void setToLuPayStartTime(Date toLuPayStartTime) {
        this.toLuPayStartTime = toLuPayStartTime;
    }

    public Date getToLuPayEndTime() {
        return toLuPayEndTime;
    }

    public void setToLuPayEndTime(Date toLuPayEndTime) {
        this.toLuPayEndTime = toLuPayEndTime;
    }

    public Integer getToLuPayState() {
        return toLuPayState;
    }

    public void setToLuPayState(Integer toLuPayState) {
        this.toLuPayState = toLuPayState;
    }

    public String getToRddOutId() {
        return toRddOutId;
    }

    public void setToRddOutId(String toRddOutId) {
        this.toRddOutId = toRddOutId == null ? null : toRddOutId.trim();
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId == null ? null : returnOrderId.trim();
    }

    public String getTradingId() {
        return tradingId;
    }

    public void setTradingId(String tradingId) {
        this.tradingId = tradingId == null ? null : tradingId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo == null ? null : orderInfo.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}