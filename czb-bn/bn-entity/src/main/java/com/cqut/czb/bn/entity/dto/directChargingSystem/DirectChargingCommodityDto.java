package com.cqut.czb.bn.entity.dto.directChargingSystem;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DirectChargingCommodityDto {
    private String commodityId;

    private Double account;

    private Double discount;

    private Double preferentialPrice;

    private Integer state;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    private Integer commodityType;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(Double preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
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

    public Integer getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
    }
}
