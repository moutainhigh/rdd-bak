package com.cqut.czb.bn.entity.entity.withoutCard;

import java.util.Date;

public class WithoutCardAreaConfig {
    private String petrolConfigId;

    private Integer petrolType;

    private String area;

    private Integer saleState;

    private Date createAt;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}