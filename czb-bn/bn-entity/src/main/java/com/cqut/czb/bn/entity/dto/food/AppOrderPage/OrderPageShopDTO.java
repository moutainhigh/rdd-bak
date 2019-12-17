package com.cqut.czb.bn.entity.dto.food.AppOrderPage;

import java.util.Date;
import java.util.List;

/**
 * 商店信息,
 */
public class OrderPageShopDTO {

    private String shopId;

    private String userId;

    private String shopName;

    private String shopPhone;

    private String shopContent;

    private String shopAddress;

    private Integer audit;

    private Integer shopType;

    private Double longitude;

    private Double latitude;

    private Date startingTimeBusiness;

    private Date endTimeBusiness;

    private String shopImg;

    private List<String> topImg;

    private String Distance;

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

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

    public List<String> getTopImg() {
        return topImg;
    }

    public void setTopImg(List<String> topImg) {
        this.topImg = topImg;
    }
}
