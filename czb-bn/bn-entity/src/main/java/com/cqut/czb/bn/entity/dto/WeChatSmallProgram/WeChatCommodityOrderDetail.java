package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class WeChatCommodityOrderDetail {
    private String orderId; // 订单id
    private String thirdOrder; // 第三方订单号
    private Integer orderState; // 订单状态
    private String commodityId; // 商品id
    private String commodityTitle; // 商品名
    private Integer commodityNum; // 商品数量
    private Double actualPrice; // 实际支付价格
    private Integer payStatus; // 支付状态
    private Integer payMethod; // 支付方式
    private String userId; // 用户id
    private String userAccount; // 用户账号(czb_user表)
    private String shopId; // 商店id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt; // 下单时间
    private String shopName;
    private String shopAddress;
    private String commodityImgId;
    private List<String> commodityImgList;
    private String commoditySource;
    private Integer commodityType; // 商品类型
    private String commodityTypeContent; // 商品类目
    private Integer takeWay; // 商品取件方式
    private String remark;

    private Integer deliveryState; // 寄送状态(如果takeWay为1,该字段替代orderState(前台))

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

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

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getCommodityImgList() {
        return commodityImgList;
    }

    public void setCommodityImgList(List<String> commodityImgList) {
        this.commodityImgList = commodityImgList;
    }

    public String getCommoditySource() {
        return commoditySource;
    }

    public void setCommoditySource(String commoditySource) {
        this.commoditySource = commoditySource;
    }

    public Integer getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
    }

    public String getCommodityTypeContent() {
        return commodityTypeContent;
    }

    public void setCommodityTypeContent(String commodityTypeContent) {
        this.commodityTypeContent = commodityTypeContent;
    }

    public Integer getTakeWay() {
        return takeWay;
    }

    public void setTakeWay(Integer takeWay) {
        this.takeWay = takeWay;
    }

    public String getCommodityImgId() {
        return commodityImgId;
    }

    public void setCommodityImgId(String commodityImgId) {
        this.commodityImgId = commodityImgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }

    public Integer getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(Integer deliveryState) {
        this.deliveryState = deliveryState;
    }
}
