package com.cqut.czb.bn.entity.entity.weChatSmallProgram;

import java.util.Date;

public class WeChatCommodityOrder {
    private String orderId;

    private String userId;

    private String commodityId;

    private String shopId;

    private Double actualPrice;

    private String thirdOrder;

    private Integer payStatus;

    private Integer payMethod;

    private String remark;

    private String electronicCode;

    private Integer orderState;

    private String addressId;

    private String qrcode;

    private String phone;

    private String commoditySource;

    private Double fyMoney;

    private Double costPrice;

    private Integer commodityType;

    private String commmodityTypeId;

    private Date processingTime;

    private String handler;

    private Integer commodityNum;

    private Date createAt;

    private Date updateAt;

    private Integer isSettlement;

    private Integer isHaveSettled;

    private String settledRecordId;

    private String attrInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder == null ? null : thirdOrder.trim();
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getElectronicCode() {
        return electronicCode;
    }

    public void setElectronicCode(String electronicCode) {
        this.electronicCode = electronicCode == null ? null : electronicCode.trim();
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCommoditySource() {
        return commoditySource;
    }

    public void setCommoditySource(String commoditySource) {
        this.commoditySource = commoditySource == null ? null : commoditySource.trim();
    }

    public Double getFyMoney() {
        return fyMoney;
    }

    public void setFyMoney(Double fyMoney) {
        this.fyMoney = fyMoney;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
    }

    public String getCommmodityTypeId() {
        return commmodityTypeId;
    }

    public void setCommmodityTypeId(String commmodityTypeId) {
        this.commmodityTypeId = commmodityTypeId == null ? null : commmodityTypeId.trim();
    }

    public Date getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Date processingTime) {
        this.processingTime = processingTime;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    public Integer getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
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

    public Integer getIsSettlement() {
        return isSettlement;
    }

    public void setIsSettlement(Integer isSettlement) {
        this.isSettlement = isSettlement;
    }

    public Integer getIsHaveSettled() {
        return isHaveSettled;
    }

    public void setIsHaveSettled(Integer isHaveSettled) {
        this.isHaveSettled = isHaveSettled;
    }

    public String getSettledRecordId() {
        return settledRecordId;
    }

    public void setSettledRecordId(String settledRecordId) {
        this.settledRecordId = settledRecordId == null ? null : settledRecordId.trim();
    }

    public String getAttrInfo() {
        return attrInfo;
    }

    public void setAttrInfo(String attrInfo) {
        this.attrInfo = attrInfo == null ? null : attrInfo.trim();
    }
}