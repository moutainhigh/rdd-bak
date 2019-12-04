package com.cqut.czb.bn.entity.dto.wechatAppletCommodity;

import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class WxCommodityDTO {
    private String commodityId;

    private String shopId;

    private String commodityTitle;

    private String commodityInfo;

    private Double discount;

    private String commodityImgId;

    private Double costPrice;

    private Double originalPrice;

    private Double salePrice;

    private String commmodityTypeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startingTimeBusiness;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTimeBusiness;

    private String commoditySource;

    private Integer commodityNum;

    private Double fyMoney;

    private Integer isSale;

    private Integer takeWay;

    private String shopName;

    private String shopPhone;

    private String shopContent;

    private String shopAddress;

    private Double longitude;

    private Double latitude;

    private Integer isHaveShop;

    private String showPlace;

    private Integer showOrder;

    private List<FileFunctionDTO> recommendImgs;

    private List<FileFunctionDTO> guessImgs;

    private List<FileFunctionDTO> posterImgs;

    private Date createAt;

    private Date updateAt;

    private String deleteIds;

    /**
     * 1 推荐 2 猜你喜欢 3 海报图片
     */
    private Integer insertType;

    private String userId;

    public List<FileFunctionDTO> getRecommendImgs() {
        return recommendImgs;
    }

    public void setRecommendImgs(List<FileFunctionDTO> recommendImgs) {
        this.recommendImgs = recommendImgs;
    }

    public List<FileFunctionDTO> getGuessImgs() {
        return guessImgs;
    }

    public void setGuessImgs(List<FileFunctionDTO> guessImgs) {
        this.guessImgs = guessImgs;
    }

    public List<FileFunctionDTO> getPosterImgs() {
        return posterImgs;
    }

    public void setPosterImgs(List<FileFunctionDTO> posterImgs) {
        this.posterImgs = posterImgs;
    }

    public Double getFyMoney() {
        return fyMoney;
    }

    public void setFyMoney(Double fyMoney) {
        this.fyMoney = fyMoney;
    }

    public Integer getInsertType() {
        return insertType;
    }

    public void setInsertType(Integer insertType) {
        this.insertType = insertType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(String deleteIds) {
        this.deleteIds = deleteIds;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
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

    public String getCommodityImgId() {
        return commodityImgId;
    }

    public void setCommodityImgId(String commodityImgId) {
        this.commodityImgId = commodityImgId == null ? null : commodityImgId.trim();
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getCommmodityTypeId() {
        return commmodityTypeId;
    }

    public void setCommmodityTypeId(String commmodityTypeId) {
        this.commmodityTypeId = commmodityTypeId == null ? null : commmodityTypeId.trim();
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

    public String getCommoditySource() {
        return commoditySource;
    }

    public void setCommoditySource(String commoditySource) {
        this.commoditySource = commoditySource == null ? null : commoditySource.trim();
    }

    public Integer getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Integer getTakeWay() {
        return takeWay;
    }

    public void setTakeWay(Integer takeWay) {
        this.takeWay = takeWay;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone == null ? null : shopPhone.trim();
    }

    public String getShopContent() {
        return shopContent;
    }

    public void setShopContent(String shopContent) {
        this.shopContent = shopContent == null ? null : shopContent.trim();
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? null : shopAddress.trim();
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

    public Integer getIsHaveShop() {
        return isHaveShop;
    }

    public void setIsHaveShop(Integer isHaveShop) {
        this.isHaveShop = isHaveShop;
    }

    public String getShowPlace() {
        return showPlace;
    }

    public void setShowPlace(String showPlace) {
        this.showPlace = showPlace == null ? null : showPlace.trim();
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
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
}
