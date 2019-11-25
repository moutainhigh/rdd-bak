package com.cqut.czb.bn.entity.dto.WeChatCommodity;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-25 14:56
 */
public class WCPCommodityOutputDTO {
    private String commodityId;

    private String commodityTitle;

    private String commodityInfo;

    private String originalPrice;

    private String salePrice;

    private Integer takeWay;

    private String distance;

    private String shopName;

    private String shopContent;

    private String shopAddress;

    private String commodityNum;

    private String commodityImgId;

    private String longitude;

    private String latitude;

    private Date endTimeBusiness;

    private Date startingTimeBusiness;

    private Double fyMoney;

    private String salesVolume;

    private List<String> commodityImg;

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Double getFyMoney() {
        return fyMoney;
    }

    public void setFyMoney(Double fyMoney) {
        this.fyMoney = fyMoney;
    }

    public String getCommodityImgId() {
        return commodityImgId;
    }

    public void setCommodityImgId(String commodityImgId) {
        this.commodityImgId = commodityImgId;
    }

    public List<String> getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(List<String> commodityImg) {
        this.commodityImg = commodityImg;
    }

    public Date getEndTimeBusiness() {
        return endTimeBusiness;
    }

    public void setEndTimeBusiness(Date endTimeBusiness) {
        this.endTimeBusiness = endTimeBusiness;
    }

    public Date getStartingTimeBusiness() {
        return startingTimeBusiness;
    }

    public void setStartingTimeBusiness(Date startingTimeBusiness) {
        this.startingTimeBusiness = startingTimeBusiness;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public String getCommodityInfo() {
        return commodityInfo;
    }

    public void setCommodityInfo(String commodityInfo) {
        this.commodityInfo = commodityInfo;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getTakeWay() {
        return takeWay;
    }

    public void setTakeWay(Integer takeWay) {
        this.takeWay = takeWay;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        this.commodityNum = commodityNum;
    }

}