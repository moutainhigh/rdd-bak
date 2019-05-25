package com.cqut.czb.bn.entity.dto.appHomePage;

public class petrolPriceReportDTO {

    private String area;//所属地区

    private Double petrolPrice;//油单价

    private String petrolName;//油名称

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(Double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getPetrolName() {
        return petrolName;
    }

    public void setPetrolName(String petrolName) {
        this.petrolName = petrolName;
    }
}
