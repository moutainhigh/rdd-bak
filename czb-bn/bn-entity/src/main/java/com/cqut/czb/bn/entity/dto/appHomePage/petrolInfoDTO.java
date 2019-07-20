package com.cqut.czb.bn.entity.dto.appHomePage;

public class petrolInfoDTO {

    private Double petrolDenomination;//油价面额(原价)

    private Double petrolPrice;//油卡价格

    private double vipPrice ;//vip价格

    private double discount;//折扣

    private String fangyong1;//一级返佣比例

    private String fangyong2;//二级返佣比例

    private double FYmoney1;//一级返佣的钱

    private double FYmoney2;//一级返佣的钱

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getFangyong1() {
        return fangyong1;
    }

    public void setFangyong1(String fangyong1) {
        this.fangyong1 = fangyong1;
    }

    public String getFangyong2() {
        return fangyong2;
    }

    public void setFangyong2(String fangyong2) {
        this.fangyong2 = fangyong2;
    }

    public double getFYmoney1() {
        return FYmoney1;
    }

    public void setFYmoney1(double FYmoney1) {
        this.FYmoney1 = FYmoney1;
    }

    public double getFYmoney2() {
        return FYmoney2;
    }

    public void setFYmoney2(double FYmoney2) {
        this.FYmoney2 = FYmoney2;
    }

    public Double getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(Double petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public Double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(Double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }
}
