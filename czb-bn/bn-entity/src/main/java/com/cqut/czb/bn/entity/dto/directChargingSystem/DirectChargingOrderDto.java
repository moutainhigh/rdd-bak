package com.cqut.czb.bn.entity.dto.directChargingSystem;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DirectChargingOrderDto extends PageDTO {
    private String orderId;

    private String thirdOrderId;

    private String userAccount;

    private Double rechargeAmount;

    private Double realPrice;

    private String sinopecPetrolNum;

    private String petrolChinaPetrolNum;

    private String userId;

    private Integer paymentMethod;

    private Integer state;

    private String superiorAccount; // 推荐人电话

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date createAt;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date updateAt;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    private Integer recordType; // 1 话费 2 中石油 3 中石化

    public String getOrderId() {
        return orderId;
    }

    public Double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId == null ? null : thirdOrderId.trim();
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSinopecPetrolNum() {
        return sinopecPetrolNum;
    }

    public void setSinopecPetrolNum(String sinopecPetrolNum) {
        this.sinopecPetrolNum = sinopecPetrolNum;
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

    public String getPetrolChinaPetrolNum() {
        return petrolChinaPetrolNum;
    }

    public void setPetrolChinaPetrolNum(String petrolChinaPetrolNum) {
        this.petrolChinaPetrolNum = petrolChinaPetrolNum;
    }

    public String getSuperiorAccount() {
        return superiorAccount;
    }

    public void setSuperiorAccount(String superiorAccount) {
        this.superiorAccount = superiorAccount;
    }
}
