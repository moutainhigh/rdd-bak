package com.cqut.czb.bn.entity.entity;

public class PetrolPriceReport {
    private String petrolPriceReportId;

    private String petrolName;

    private Double petrolPrice;

    private String area;

    public String getPetrolPriceReportId() {
        return petrolPriceReportId;
    }

    public void setPetrolPriceReportId(String petrolPriceReportId) {
        this.petrolPriceReportId = petrolPriceReportId == null ? null : petrolPriceReportId.trim();
    }

    public String getPetrolName() {
        return petrolName;
    }

    public void setPetrolName(String petrolName) {
        this.petrolName = petrolName == null ? null : petrolName.trim();
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
        this.area = area == null ? null : area.trim();
    }
}