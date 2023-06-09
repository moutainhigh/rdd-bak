package com.cqut.czb.bn.entity.dto.equityPayment;

import java.util.Date;

public class EquityPaymentDTO {

    // 订单号
    private String orderId;

    private String userId;

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

    //售价
    private String sellingPrice;

    // 进价
    private String purchasePrice;

    //支付价格
    private String payPrice;

    // 总交易面额
    private Integer totalPrice;

    // 商品编号
    private String goodsId;

    //订单状态
    private String orderState;

    // 商品名称
    private String goodsTitle;

    // 商品图片
    private String goodsPic;

    // 是否为游戏
    private boolean isGame;

    // 支付金额
    private Double amount;

    // 扣除积分
    private Integer integralAmount;

    // 充值类型
    private Integer rechargeType;

    // 是否为第三方
    private Integer isThird;

    // 第三方订单号
    private String thirdOrder;

    // 支付方式
    private Integer payMethod;

    // 卡密链接
    private String code;

    // 图片
    private String pic;

    private Integer goodsType;

    private Integer isBrowser;

    private Date createAt;

    private String beginDate;

    private String overDate;

    private String openId;

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

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public boolean isGame() {
        return isGame;
    }

    public void setGame(boolean game) {
        isGame = game;
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
        this.rechargeType = rechargeType;
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

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getOverDate() {
        return overDate;
    }

    public void setOverDate(String overDate) {
        this.overDate = overDate;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getIsThird() {
        return isThird;
    }

    public void setIsThird(Integer isThird) {
        this.isThird = isThird;
    }

    @Override
    public String toString() {
        return "权益商品：EquityPaymentDTO{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", productCode='" + productCode + '\'' +
                ", buyNum=" + buyNum +
                ", isCallBack=" + isCallBack +
                ", tradeType=" + tradeType +
                ", clientIP='" + clientIP + '\'' +
                ", unitPrice=" + unitPrice +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", purchasePrice='" + purchasePrice + '\'' +
                ", payPrice='" + payPrice + '\'' +
                ", totalPrice=" + totalPrice +
                ", goodsId='" + goodsId + '\'' +
                ", orderState='" + orderState + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsPic='" + goodsPic + '\'' +
                ", isGame=" + isGame +
                ", amount=" + amount +
                ", integralAmount=" + integralAmount +
                ", rechargeType=" + rechargeType +
                ", thirdOrder='" + thirdOrder + '\'' +
                ", payMethod=" + payMethod +
                ", code='" + code + '\'' +
                ", pic='" + pic + '\'' +
                ", goodsType=" + goodsType +
                ", isBrowser=" + isBrowser +
                ", createAt=" + createAt +
                ", beginDate='" + beginDate + '\'' +
                ", overDate='" + overDate + '\'' +
                '}';
    }
}
