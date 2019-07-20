package com.cqut.czb.bn.entity.dto.VipArea;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-20 20:29
 */
public class VipPriceAndNote {
    private Double VipPrice;

    private String area;

    private String note;

    public Double getVipPrice() {
        return VipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        VipPrice = vipPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}