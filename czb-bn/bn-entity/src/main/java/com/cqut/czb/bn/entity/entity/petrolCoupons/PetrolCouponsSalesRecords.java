package com.cqut.czb.bn.entity.entity.petrolCoupons;

import com.cqut.czb.bn.entity.dto.PageDTO;
import java.util.Date;

public class PetrolCouponsSalesRecords extends PageDTO {
    private String petrolNum;//油卡号码
    private String petrolKind;//油卡种类 0 国通，1 中石油，2 中石化
    private String petrolDenomination;//油卡面额
    private String petrolPrice;//油卡价格
    private String owner;//购买者
    private String ownerId;//购买者
    private String paymentMethod;//支付方式
    private Date transactionTime;//交易时间
    private String area;//地区
    private String turnoverAmount;
    private Date createAt;
    private String remark;
    private Integer recordType; // 是否第一次购买
    private String thirdOrderId;

    private String toRddOutTradeNo; // （用户向人多多）商家订单号
    private String toRddThirdOrderId; // （用户向人多多）第三方订单号
    private Double toRddTurnoverAmount; // （用户向人多多）成交金额
    private Date toRddTransactionTime; // （用户向人多多）交易时间
    private Integer toRddState; // （用户向人多多）交易状态 0 没支付，1 支付成功
    private Double unitPrice; // （人多多向璐付）成交金额
    private Date toLuPayStartTime; // （人多多向璐付）交易开始时间
    private Date toLuPayEndTime; // （人多多向璐付）交易结束时间
    private Integer toLuPayState; // （人多多向璐付）交易状态
    private String toRddOutId; // （人多多向璐付）合作方订单编号
    private String returnOrderId; // 缴费订单编号
    private String tradingId; // 缴费交易流水编号
    private String orderId; // 璐付系统订单号
    private String orderInfo; // 券短链接
    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getToRddOutTradeNo() {
        return toRddOutTradeNo;
    }

    public void setToRddOutTradeNo(String toRddOutTradeNo) {
        this.toRddOutTradeNo = toRddOutTradeNo;
    }

    public String getToRddThirdOrderId() {
        return toRddThirdOrderId;
    }

    public void setToRddThirdOrderId(String toRddThirdOrderId) {
        this.toRddThirdOrderId = toRddThirdOrderId;
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
}