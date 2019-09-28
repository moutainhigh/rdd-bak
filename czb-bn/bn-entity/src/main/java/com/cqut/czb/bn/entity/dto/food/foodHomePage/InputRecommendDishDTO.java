package com.cqut.czb.bn.entity.dto.food.foodHomePage;

public class InputRecommendDishDTO {

    private String shopId;

    private Double longitude; //经度

    private Double latitude;  //纬度


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
