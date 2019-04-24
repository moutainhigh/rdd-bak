package com.cqut.czb.bn.entity.dto.appHomePage;

import java.util.Date;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/22
 * 作用：用卡专区数据
 */
public class PetrolZoneDTO {

    private Integer petrolKind;

    private Double petrolDenomination;

    private Double petrolPrice;

    private Double discount;

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

}
