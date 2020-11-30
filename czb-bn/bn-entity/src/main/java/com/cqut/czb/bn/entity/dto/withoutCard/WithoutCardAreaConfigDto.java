package com.cqut.czb.bn.entity.dto.withoutCard;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WithoutCardAreaConfigDto extends PageDTO {
    private String petrolConfigId;

    private Integer petrolType;

    private String area;

    private Integer saleState;

    private Double discount;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date createAt;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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
