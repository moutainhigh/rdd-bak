package com.cqut.czb.bn.entity.dto.petrolManagement;

import com.cqut.czb.bn.entity.entity.Petrol;

public class PetrolManagementInputDTO {
    private String petrolIds;

    private Double petrolDenomination;

    private Double petrolPrice;

    private Integer petrolNum;

    private Integer petrolKind;

    private String area;

    private Integer isSpecialPetrol;

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

    public Integer getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(Integer petrolNum) {
        this.petrolNum = petrolNum;
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPetrolIds() {
        return petrolIds;
    }

    public void setPetrolIds(String petrolIds) {
        this.petrolIds = petrolIds;
    }

    public Integer getIsSpecialPetrol() {
        return isSpecialPetrol;
    }

    public void setIsSpecialPetrol(Integer isSpecialPetrol) {
        this.isSpecialPetrol = isSpecialPetrol;
    }
}
