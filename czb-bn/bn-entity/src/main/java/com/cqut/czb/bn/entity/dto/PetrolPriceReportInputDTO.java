package com.cqut.czb.bn.entity.dto;

public class PetrolPriceReportInputDTO extends PageDTO {

    private String petrolPriceReportId;

    private String petrolName;

    private Double petrolPrice;

    private String area;

    public String getPetrolPriceReportId() {
        return petrolPriceReportId;
    }

    public void setPetrolPriceReportId(String petrolPriceReportId) {
        this.petrolPriceReportId = petrolPriceReportId;
    }

    public String getPetrolName() {
        return petrolName;
    }

    public void setPetrolName(String petrolName) {
        this.petrolName = petrolName;
    }

    public Double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(Double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
