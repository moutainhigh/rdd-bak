package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WeChatCommodityOrderDTO {
    private String orderId; // 订单id
    private String thirdOrder; // 第三方订单号
    private Integer orderState; // 订单状态
    private String commodityId; // 商品id
    private String commodityTitle; // 商品名
    private Double actualPrice; // 实际支付价格
    private Integer payStatus; // 支付状态
    private Integer peyMethod; // 支付方式
    private String userId; // 用户id
    private String userAccount; // 用户账号(czb_user表)
    private String shopId; // 商店id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt; // 下单时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime; // 开始时间(查询使用)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime; // 结束时间(查询使用)

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPeyMethod() {
        return peyMethod;
    }

    public void setPeyMethod(Integer peyMethod) {
        this.peyMethod = peyMethod;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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
