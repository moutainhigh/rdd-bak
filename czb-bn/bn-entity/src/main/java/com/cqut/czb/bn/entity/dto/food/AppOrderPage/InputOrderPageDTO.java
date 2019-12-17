package com.cqut.czb.bn.entity.dto.food.AppOrderPage;

//距离测算
public class InputOrderPageDTO {

    private String shopId;

    private Double longitude;

    private Double latitude;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
