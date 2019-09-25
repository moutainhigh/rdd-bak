package com.cqut.czb.bn.entity.dto.food.foodHomePage;

public class SearchInputDTO {
    private Integer sortType;

    private String shopDishName;

    private String shopId;


    private Double longitude; //(app传入)

    private Double latitude;  //(app传入)

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getShopDishName() {
        return shopDishName;
    }

    public void setShopDishName(String shopDishName) {
        this.shopDishName = shopDishName;
    }

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
