package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class ThirdOrder {

    String userId;

    String orderId;

    String phone;

    String card;

    Double amount;

    String sign;

    String notifyUrl;

    @Override
    public String toString() {
        return "ThirdOrder{" +
                "userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", phone='" + phone + '\'' +
                ", card='" + card + '\'' +
                ", amount=" + amount +
                ", sign='" + sign + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
