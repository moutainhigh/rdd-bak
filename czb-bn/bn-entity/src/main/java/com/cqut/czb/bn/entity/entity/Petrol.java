package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class Petrol {
    private String petrolId;

    private String petrolNum;

    private String petrolPsw;

    private Integer petrolKind;

    private Double petrolDenomination;

    private Double petrolPrice;

    private Integer state;

    private Double discount;

    private String area;

    private String ownerId;

    private Integer petrolType;

    private Date createAt;

    private Date updateAt;

    private long endTime;

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId == null ? null : petrolId.trim();
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum == null ? null : petrolNum.trim();
    }

    public String getPetrolPsw() {
        return petrolPsw;
    }

    public void setPetrolPsw(String petrolPsw) {
        this.petrolPsw = petrolPsw == null ? null : petrolPsw.trim();
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
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