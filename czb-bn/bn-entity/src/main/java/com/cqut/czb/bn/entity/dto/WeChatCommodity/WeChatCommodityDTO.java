package com.cqut.czb.bn.entity.dto.WeChatCommodity;

import java.util.Date;
import java.util.List;


public class WeChatCommodityDTO {
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

    private String shopPhone;

    private String commodityNum;

    private String commodityImgId;

    private String longitude;

    private String latitude;

    private Date endTimeBusiness;

    private Date startingTimeBusiness;

    private Integer fyMoney;

    private Double totalFyMoney;

    private Integer salesVolume;

    private String isHaveShop;

    private String commodityIntroduce;

    private String content;

    private String area;

    private int limitedNum;

    private int idLimitedNum;

    private int limitedType;

    private int page;

    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    List<WCPCommodityAttrName> attrs;

    public List<WCPCommodityAttrName> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<WCPCommodityAttrName> attrs) {
        this.attrs = attrs;
    }

    public Double getTotalFyMoney() {
        return totalFyMoney;
    }

    public void setTotalFyMoney(Double totalFyMoney) {
        this.totalFyMoney = totalFyMoney;
    }

    public String getCommodityIntroduce() {
        return commodityIntroduce;
    }

    public void setCommodityIntroduce(String commodityIntroduce) {
        this.commodityIntroduce = commodityIntroduce;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    private String posterImg;

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    private List<String> commodityImg;

    public String getIsHaveShop() {
        return isHaveShop;
    }

    public void setIsHaveShop(String isHaveShop) {
        this.isHaveShop = isHaveShop;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Integer getFyMoney() {
        return fyMoney;
    }

    public void setFyMoney(Integer fyMoney) {
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

    public int getLimitedNum() {
        return limitedNum;
    }

    public void setLimitedNum(int limitedNum) {
        this.limitedNum = limitedNum;
    }

    public int getIdLimitedNum() {
        return idLimitedNum;
    }

    public void setIdLimitedNum(int idLimitedNum) {
        this.idLimitedNum = idLimitedNum;
    }

    public int getLimitedType() {
        return limitedType;
    }

    public void setLimitedType(int limitedType) {
        this.limitedType = limitedType;
    }
}