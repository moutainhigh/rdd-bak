package com.cqut.czb.bn.entity.dto.ThirdBusiness;

import java.util.Date;

public class GetUnChargeOrderDTO {

    //订单id
    private String orderId;

    //油卡号
    private String petrolNum;

    //购买者电话
    private String buyerPhone;

    //充值金额
    private Double money;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
