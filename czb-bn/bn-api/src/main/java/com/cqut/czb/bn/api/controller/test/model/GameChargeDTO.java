package com.cqut.czb.bn.api.controller.test.model;

/**
 * @author Liyan
 */
public class GameChargeDTO {
    private Integer tradeType;
    private String account;
    private Integer unitPrice;
    private Integer buyNum;
    private String orderId;
    private Integer isCallBack;
    private String goodsId;


    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getIsCallBack() {
        return isCallBack;
    }

    public void setIsCallBack(Integer isCallBack) {
        this.isCallBack = isCallBack;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
