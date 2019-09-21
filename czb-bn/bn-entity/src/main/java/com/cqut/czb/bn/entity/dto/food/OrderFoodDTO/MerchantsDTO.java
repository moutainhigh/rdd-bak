package com.cqut.czb.bn.entity.dto.food.OrderFoodDTO;

import java.util.Date;

/**
 * 商店信息
 */
public class MerchantsDTO {

    //商店展示id
    private String commodityId;

    //对应图片id返回
    private String commodityImg;

    //商店类型
    private Integer commmodityType;

    //经度
    private Double longitude;

    //纬度
    private Double latitude;

    //营业开始时间
    private Date startingTimeBusiness;

    //营业结束时间
    private Date endTimeBusiness;

    //商店的名字
    private String shopName;

    //商店的电话
    private String shopPhone;

    //商店的简介
    private String shopContent;

    //商店的地址
    private String shopAddress;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(String commodityImg) {
        this.commodityImg = commodityImg;
    }

    public Integer getCommmodityType() {
        return commmodityType;
    }

    public void setCommmodityType(Integer commmodityType) {
        this.commmodityType = commmodityType;
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
}
