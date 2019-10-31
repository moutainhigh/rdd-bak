package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class PartnerVipIncome {
    private String partnerVipIncomeId;

    private String partnerId;

    private Integer partnerType;

    private Integer isSettle;

    private Integer vipAddCount;

    private Date startTime;

    private Date endTime;

    private Double vipAddIncome;

    private Date createAt;

    private Date updateAt;

    private Double firstVipIncome;

    private Double secondVipIncome;

    private Double firstPetrolIncome;

    private Double secondPetrolIncome;

    public String getPartnerVipIncomeId() {
        return partnerVipIncomeId;
    }

    public void setPartnerVipIncomeId(String partnerVipIncomeId) {
        this.partnerVipIncomeId = partnerVipIncomeId == null ? null : partnerVipIncomeId.trim();
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId == null ? null : partnerId.trim();
    }

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public Integer getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Integer isSettle) {
        this.isSettle = isSettle;
    }

    public Integer getVipAddCount() {
        return vipAddCount;
    }

    public void setVipAddCount(Integer vipAddCount) {
        this.vipAddCount = vipAddCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getVipAddIncome() {
        return vipAddIncome;
    }

    public void setVipAddIncome(Double vipAddIncome) {
        this.vipAddIncome = vipAddIncome;
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

    public Double getFirstVipIncome() {
        return firstVipIncome;
    }

    public void setFirstVipIncome(Double firstVipIncome) {
        this.firstVipIncome = firstVipIncome;
    }

    public Double getSecondVipIncome() {
        return secondVipIncome;
    }

    public void setSecondVipIncome(Double secondVipIncome) {
        this.secondVipIncome = secondVipIncome;
    }

    public Double getFirstPetrolIncome() {
        return firstPetrolIncome;
    }

    public void setFirstPetrolIncome(Double firstPetrolIncome) {
        this.firstPetrolIncome = firstPetrolIncome;
    }

    public Double getSecondPetrolIncome() {
        return secondPetrolIncome;
    }

    public void setSecondPetrolIncome(Double secondPetrolIncome) {
        this.secondPetrolIncome = secondPetrolIncome;
    }
}