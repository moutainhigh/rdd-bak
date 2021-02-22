package com.cqut.czb.bn.entity.dto.equityPayment;

import java.util.Date;

public class EquityPaymentDTO {

    // 订单号
    private String orderId;

    // 充值账号
    private String account;

    // 商品编号
    private String productCode;

    // 购买数量
    private Integer buyNum;

    // 是否回调
    private Integer isCallBack;

    // 充值类型
    private Integer tradeType;

    // 客户端ip
    private String clientIP;

    // 单位面值
    private Double unitPrice;

    // 总交易面额
    private Integer totalPrice;

    // 商品编号
    private String goodsId;

    // 支付金额
    private Double amount;

    // 扣除积分
    private Integer integralAmount;

    // 充值类型
    private Integer rechargeType;

    // 第三方订单号
    private String thirdOrder;

    private Integer isBrowser;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getIsCallBack() {
        return isCallBack;
    }

    public void setIsCallBack(Integer isCallBack) {
        this.isCallBack = isCallBack;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(Integer integralAmount) {
        this.integralAmount = integralAmount;
    }

    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        rechargeType = rechargeType;
    }

    public Integer getIsBrowser() {
        return isBrowser;
    }

    public void setIsBrowser(Integer isBrowser) {
        this.isBrowser = isBrowser;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }
}
