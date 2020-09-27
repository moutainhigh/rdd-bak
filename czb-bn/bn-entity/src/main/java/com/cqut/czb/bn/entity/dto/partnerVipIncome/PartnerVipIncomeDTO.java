package com.cqut.czb.bn.entity.dto.partnerVipIncome;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PartnerVipIncomeDTO {
    private String partnerVipIncomeId;

    private String partnerId;

    private String userAccount;

    private String userName;

    private Integer partnerType;

    private Integer isSettle;

    private Integer vipAddCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Double vipAddIncome;

    private String settleIds;  //批量结算id串

    private Double firstVipIncome;

    private Double secondVipIncome;

    private Double firstPetrolIncome;

    private Double secondPetrolIncome;

    private Double vipTotalMoney;    //合伙人展示用vip总收入

    private Double petrolTotalMoney; //合伙人展示用油卡总收入

    private Date createAt;

    private Date updateAt;

    private Integer isSpecial;

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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSettleIds() {
        return settleIds;
    }

    public void setSettleIds(String settleIds) {
        this.settleIds = settleIds;
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

    public Double getVipTotalMoney() {
        return vipTotalMoney;
    }

    public void setVipTotalMoney(Double vipTotalMoney) {
        this.vipTotalMoney = vipTotalMoney;
    }

    public Double getPetrolTotalMoney() {
        return petrolTotalMoney;
    }

    public void setPetrolTotalMoney(Double petrolTotalMoney) {
        this.petrolTotalMoney = petrolTotalMoney;
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

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }
}
