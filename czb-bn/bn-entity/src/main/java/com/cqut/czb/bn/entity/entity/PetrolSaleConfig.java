package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class PetrolSaleConfig {
    private String petrolConfigId;

    private Integer petrolType;

    private String area;

    private Integer saleState;

    private Date ctreateAt;

    private Date updateAt;

    public String getPetrolConfigId() {
        return petrolConfigId;
    }

    public void setPetrolConfigId(String petrolConfigId) {
        this.petrolConfigId = petrolConfigId == null ? null : petrolConfigId.trim();
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }

    public Date getCtreateAt() {
        return ctreateAt;
    }

    public void setCtreateAt(Date ctreateAt) {
        this.ctreateAt = ctreateAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}