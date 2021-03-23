package com.cqut.czb.bn.entity.dto;

import java.util.Date;

public class H5StockDTO {
    private String stockId;
    private String commodityId;
    private String content;
    private int state;
    private Date createAt;
    private Date updateAt;
    private int recordType;
    private String userId;
    private double price;
    private double payPrice;
    private int integralAmount;
    private int isBrowser;
    private String thirdOrder;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
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

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public int getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(int integralAmount) {
        this.integralAmount = integralAmount;
    }

    public int getIsBrowser() {
        return isBrowser;
    }

    public void setIsBrowser(int isBrowser) {
        this.isBrowser = isBrowser;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }
}
