package com.cqut.czb.bn.entity.dto.appBuyService;

public class BuyServiceDTO {

    private String userId;

    private String commodityId;//商品的id

    private String shopId;

    private Double commodityPrice;

    private String classification;

    private String inputInfo;
//    "[{'infoTitle':'a','infoContent':'aa'},{'infoTitle':'b','infoContent':'bb'}]"

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getInputInfo() {
        return inputInfo;
    }

    public void setInputInfo(String inputInfo) {
        this.inputInfo = inputInfo;
    }


}
