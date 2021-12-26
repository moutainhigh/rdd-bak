package com.cqut.czb.bn.entity.dto.autoRecharge;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserRechargeDTO extends PageDTO {
    private String recordId; // 记录id

    private String petrolNums;

    private String petrolNum; // 油卡号

    private String buyerId; // 充值人id

    private int paymentMethod;  // 支付方式

    private String thirdOrderId;  // 订单号

    private double turnoverAmount;  // 充值金额

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date transactionTime; // 购买时间

    private String date; // 购买时间

    private String petrolId;  // 油卡id

    private Integer state;  // 交易状态

    private String contractId;

    private Date createAt;

    private Date updateAt;

    private int recordType;  // 交易状态

    private Integer isRecharged;  // 交易状态

    private int petrolKind;  // 交易状态

    private double denomination;  // 充值金额

    private double currentPrice;  // 充值金额

    private String regional;//地址

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetrolNums() {
        return petrolNums;
    }

    public void setPetrolNums(String petrolNums) {
        this.petrolNums = petrolNums;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public double getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(double turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public Integer getIsRecharged() {
        return isRecharged;
    }

    public void setIsRecharged(Integer isRecharged) {
        this.isRecharged = isRecharged;
    }

    public int getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(int petrolKind) {
        this.petrolKind = petrolKind;
    }

    public double getDenomination() {
        return denomination;
    }

    public void setDenomination(double denomination) {
        this.denomination = denomination;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "UserRechargeDTO{" +
                "recordId='" + recordId + '\'' +
                ",\n petrolNums='" + petrolNums + '\'' +
                ",\n petrolNum='" + petrolNum + '\'' +
                ",\n buyerId='" + buyerId + '\'' +
                ",\n paymentMethod=" + paymentMethod +
                ",\n thirdOrderId='" + thirdOrderId + '\'' +
                ",\n turnoverAmount=" + turnoverAmount +
                ",\n transactionTime=" + transactionTime +
                ",\n date='" + date + '\'' +
                ",\n petrolId='" + petrolId + '\'' +
                ",\n state=" + state +
                ",\n contractId='" + contractId + '\'' +
                ",\n createAt=" + createAt +
                ",\n updateAt=" + updateAt +
                ",\n recordType=" + recordType +
                ",\n isRecharged=" + isRecharged +
                ",\n petrolKind=" + petrolKind +
                ",\n denomination=" + denomination +
                ",\n currentPrice=" + currentPrice +
                ",\n regional=" + regional +
                '}';
    }
}
