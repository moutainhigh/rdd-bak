package com.cqut.czb.bn.entity.dto.petrolCoupons;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PetrolCouponsSales extends PageDTO {
    private String recordId;
    private String petrolId;//油卡号码
    private String userAccount;//购买人电话
    private String paymentMethod;//支付方式
    private String toRddThirdOrderId; // 支付订单号
    private Double petrolDenomination;//油卡面额
    private Double petrolPrice;//油卡价格
    private Double toRddTurnoverAmount; // 实际支付金额金额
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date toRddTransactionTime; // 购买时间
    private Double unitPrice; // 充值金额
    private String toLuPayState; // 充值状态
    private String returnOrderId; // 缴费订单编号
    private String tradingId; // 缴费交易流水编号
    private String orderId; // 璐付系统订单号
    private String area;//地区

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date startTime; // 购买时间
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date endTime; // 购买时间

    private Double luPayBalance; // 余额

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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getToRddThirdOrderId() {
        return toRddThirdOrderId;
    }

    public void setToRddThirdOrderId(String toRddThirdOrderId) {
        this.toRddThirdOrderId = toRddThirdOrderId;
    }

    public Double getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(Double petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public Double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(Double petrolPrice) {
        this.petrolPrice = petrolPrice;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getToLuPayState() {
        return toLuPayState;
    }

    public void setToLuPayState(String toLuPayState) {
        this.toLuPayState = toLuPayState;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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


    public Double getLuPayBalance() {
        return luPayBalance;
    }

    public void setLuPayBalance(Double luPayBalance) {
        this.luPayBalance = luPayBalance;
    }
}