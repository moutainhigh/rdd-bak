package com.cqut.czb.bn.entity.dto;

import java.util.Date;

public class CouponsSaleRecordsDTO {
    private String recordId;//记录ID

    private String petrolId;//卡卷ID

    private String buyerId;//用户id

    private String userAccount;//用户电话

    private Integer paymentMethod;//用户支付方式,1 支付宝，2 微信

    private String toRddOrderId;//（用户向人多多）商家订单号

    private String thirdOrderId;//（用户向人多多）第三方订单号

    private Double turnoverAmount;//（用户向人多多）成交金额

    private Date transactionTime;//（用户向人多多）交易时间

    private Double unitPrice;//（人多多向璐付）成交金额

    private Date startTime;//（人多多向璐付）交易开始时间

    private Date endTime;//（人多多向璐付）交易结束时间

    private String toRddOutId;//（人多多向璐付）合作方订单编号

    private String returnOrderId;//缴费订单编号

    private String tradingId;//缴费交易流水编号

    private String orderId;//璐付系统订单号

    private String orderInfo;//券短链接

    private Date createAt;//创建时间

    private Date updateAt;//修改时间

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
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

    public String getToRddOrderId() {
        return toRddOrderId;
    }

    public void setToRddOrderId(String toRddOrderId) {
        this.toRddOrderId = toRddOrderId;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public Double getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(Double turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getToRddOutId() {
        return toRddOutId;
    }

    public void setToRddOutId(String toRddOutId) {
        this.toRddOutId = toRddOutId;
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getTradingId() {
        return tradingId;
    }

    public void setTradingId(String tradingId) {
        this.tradingId = tradingId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
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
