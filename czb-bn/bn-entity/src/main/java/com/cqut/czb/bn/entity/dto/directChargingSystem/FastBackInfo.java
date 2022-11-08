package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class FastBackInfo {

    String state;
    String orderType;
    String merchantId;
    String outTradeNo;
    String amount;
    String cardNo;
    String orderNo;
    String requestTime;

    @Override
    public String toString() {
        return "FastBackInfo{" +
                "state='" + state + '\'' +
                ", orderType='" + orderType + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", amount='" + amount + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", requestTime='" + requestTime + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
