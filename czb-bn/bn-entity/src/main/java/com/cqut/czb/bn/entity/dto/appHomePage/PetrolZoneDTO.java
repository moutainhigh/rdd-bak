package com.cqut.czb.bn.entity.dto.appHomePage;

import java.util.List;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/22
 * 作用：用卡专区数据
 */
public class PetrolZoneDTO {

    private String petrolName;

    private Integer petrolKind;

    private List<Double> petrolPrice;

    public String getPetrolName() {
        return petrolName;
    }

    public void setPetrolName(String petrolName) {
        this.petrolName = petrolName;
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public List<Double> getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(List<Double> petrolPrice) {
        this.petrolPrice = petrolPrice;
    }
}
