package com.cqut.czb.bn.entity.dto.withoutCard;

import com.cqut.czb.bn.entity.dto.PageDTO;

import java.util.Date;

public class CommodityWithoutCardDto extends PageDTO {
    private String commodityId;

    private String commodityTitle;

    private String commodityInfo;

    private Double discount;

    private Double vipDiscount;

    private Double commodityPrice;

    private Double commodityDenomination;

    private Integer state;


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle == null ? null : commodityTitle.trim();
    }

    public String getCommodityInfo() {
        return commodityInfo;
    }

    public void setCommodityInfo(String commodityInfo) {
        this.commodityInfo = commodityInfo == null ? null : commodityInfo.trim();
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(Double vipDiscount) {
        this.vipDiscount = vipDiscount;
    }

    public Double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public Double getCommodityDenomination() {
        return commodityDenomination;
    }

    public void setCommodityDenomination(Double commodityDenomination) {
        this.commodityDenomination = commodityDenomination;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
