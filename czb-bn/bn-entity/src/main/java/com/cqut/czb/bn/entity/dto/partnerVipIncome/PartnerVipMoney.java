package com.cqut.czb.bn.entity.dto.partnerVipIncome;

import java.util.Date;

public class PartnerVipMoney {
    private String userId;

    private String partnerId;

    private Integer partnerType;

    private String recordId;

    private Double vipConsumption;  //下级vip充值的总金额

    private Double firstVipConsumption; //事业合伙人专属收益部分的总金额

    private Double petrolMoney;   //下级油卡消费总金额

    private Date createAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Double getVipConsumption() {
        return vipConsumption;
    }

    public void setVipConsumption(Double vipConsumption) {
        this.vipConsumption = vipConsumption;
    }

    public Double getFirstVipConsumption() {
        return firstVipConsumption;
    }

    public void setFirstVipConsumption(Double firstVipConsumption) {
        this.firstVipConsumption = firstVipConsumption;
    }

    public Double getPetrolMoney() {
        return petrolMoney;
    }

    public void setPetrolMoney(Double petrolMoney) {
        this.petrolMoney = petrolMoney;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
