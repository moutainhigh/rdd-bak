package com.cqut.czb.bn.entity.dto.petrolManagement;

public class ModifyPetrolInputDTO {
    private String petrolNum;
    private String petrolPsw;
    private String petrolDenomination;//油卡面额
    private String petrolPrice;//油卡价格
    private String area;//所属地区
    private String petrolId;

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getPetrolPsw() {
        return petrolPsw;
    }

    public void setPetrolPsw(String petrolPsw) {
        this.petrolPsw = petrolPsw;
    }

    public String getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(String petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
