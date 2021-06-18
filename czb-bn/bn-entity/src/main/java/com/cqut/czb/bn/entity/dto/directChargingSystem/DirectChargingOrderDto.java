package com.cqut.czb.bn.entity.dto.directChargingSystem;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DirectChargingOrderDto extends PageDTO {
    private String oldOrderId;

    private String orderId;

    private String thirdOrderId;

    private String userAccount;

    private Double rechargeAmount;

    private Double totalRechargeAmount;

    private Double realPrice;

    private Double totalRealPrice;

    private String sinopecPetrolNum;

    private String petrolChinaPetrolNum;

    private String userId;

    private Integer paymentMethod;

    private Integer state;

    private String superiorAccount; // 推荐人电话

    private Integer isNeedLogin; // 是否需要登录

    private String rechargeAccount;

    private String cardholder;

    private Integer isBrowser;

    private Integer integralAmount;

    private Integer isDirectPartner;

    private Integer isNew;

    private String openId;

    private String commodityId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date updateAt;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;

    private Integer recordType; // 1 话费 2 中石油 3 中石化

    private String customerNumber;

    public String getOldOrderId() {
        return oldOrderId;
    }

    public void setOldOrderId(String oldOrderId) {
        this.oldOrderId = oldOrderId;
    }

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

    public Integer getIsNeedLogin() {
        return isNeedLogin;
    }

    public void setIsNeedLogin(Integer isNeedLogin) {
        this.isNeedLogin = isNeedLogin;
    }

    public String getRechargeAccount() {
        return rechargeAccount;
    }

    public void setRechargeAccount(String rechargeAccount) {
        this.rechargeAccount = rechargeAccount;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public Integer getIsBrowser() {
        return isBrowser;
    }

    public void setIsBrowser(Integer isBrowser) {
        this.isBrowser = isBrowser;
    }

    public Integer getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(Integer integralAmount) {
        this.integralAmount = integralAmount;
    }

    public Integer getIsDirectPartner() {
        return isDirectPartner;
    }

    public void setIsDirectPartner(Integer isDirectPartner) {
        this.isDirectPartner = isDirectPartner;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Double getTotalRechargeAmount() {
        return totalRechargeAmount;
    }

    public void setTotalRechargeAmount(Double totalRechargeAmount) {
        this.totalRechargeAmount = totalRechargeAmount;
    }

    public Double getTotalRealPrice() {
        return totalRealPrice;
    }

    public void setTotalRealPrice(Double totalRealPrice) {
        this.totalRealPrice = totalRealPrice;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String toString() {
        return "DirectChargingOrderDto{" +
                "oldOrderId='" + oldOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", thirdOrderId='" + thirdOrderId + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", rechargeAmount=" + rechargeAmount +
                ", realPrice=" + realPrice +
                ", sinopecPetrolNum='" + sinopecPetrolNum + '\'' +
                ", petrolChinaPetrolNum='" + petrolChinaPetrolNum + '\'' +
                ", userId='" + userId + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", state=" + state +
                ", superiorAccount='" + superiorAccount + '\'' +
                ", isNeedLogin=" + isNeedLogin +
                ", rechargeAccount='" + rechargeAccount + '\'' +
                ", cardholder='" + cardholder + '\'' +
                ", isBrowser=" + isBrowser +
                ", integralAmount=" + integralAmount +
                ", isDirectPartner=" + isDirectPartner +
                ", isNew=" + isNew +
                ", openId='" + openId + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", recordType=" + recordType +
                '}';
    }
}
