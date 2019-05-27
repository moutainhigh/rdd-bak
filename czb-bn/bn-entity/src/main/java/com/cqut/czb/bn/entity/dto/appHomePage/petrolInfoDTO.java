package com.cqut.czb.bn.entity.dto.appHomePage;

public class petrolInfoDTO {

    private Double petrolDenomination;//油价面额(原价)

    private Double petrolPrice;//油卡价格

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
