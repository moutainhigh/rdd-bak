package com.cqut.czb.bn.entity.dto.food.foodHomePage;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;

import java.util.Date;
import java.util.List;

public class SearchDishShopDTO {
    private String shopId;

    private String userId;

    private String shopName;

    private String shopPhone;

    private String shopContent;

    private String shopAddress;

    private List<DishDTO> searchDishes;

    private Date createAt;

    private Date updateAt;

    private Integer audit;

    private Integer shopType;

    private Integer sortType;  //排序方式(app传入)

    private Double sortValue;  //综合排序数值

    private Double distance;  //距离

    private String distanceWithUnit;  //换算为km与m的距离

    private Double longitude; //(app传入)

    private Double latitude;  //(app传入)

    private Date startingTimeBusiness;

    private Date endTimeBusiness;

    private String shopImg;  //店面图片

    private String savePath;  //店面图片存储路径

    private Integer saleCount; //当月销量

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopContent() {
        return shopContent;
    }

    public void setShopContent(String shopContent) {
        this.shopContent = shopContent;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public List<DishDTO> getSearchDishes() {
        return searchDishes;
    }

    public void setSearchDishes(List<DishDTO> searchDishes) {
        this.searchDishes = searchDishes;
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

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Double getSortValue() {
        return sortValue;
    }

    public void setSortValue(Double sortValue) {
        this.sortValue = sortValue;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDistanceWithUnit() {
        return distanceWithUnit;
    }

    public void setDistanceWithUnit(String distanceWithUnit) {
        this.distanceWithUnit = distanceWithUnit;
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

    public Date getStartingTimeBusiness() {
        return startingTimeBusiness;
    }

    public void setStartingTimeBusiness(Date startingTimeBusiness) {
        this.startingTimeBusiness = startingTimeBusiness;
    }

    public Date getEndTimeBusiness() {
        return endTimeBusiness;
    }

    public void setEndTimeBusiness(Date endTimeBusiness) {
        this.endTimeBusiness = endTimeBusiness;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }
}
