package com.cqut.czb.bn.entity.dto.WeChatCommodity;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-25 14:43
 */
public class WCPCommodityInputDTO {

    private String area;

    private String longitude;

    private Double latitude;

    private Double shopPlace;

    @NotNull(message = "商品ID不能为空")
    private String commodityId;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getShopPlace() {
        return shopPlace;
    }

    public void setShopPlace(Double shopPlace) {
        this.shopPlace = shopPlace;
    }
}