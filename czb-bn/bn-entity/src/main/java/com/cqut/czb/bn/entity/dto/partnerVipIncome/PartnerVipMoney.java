package com.cqut.czb.bn.entity.dto.partnerVipIncome;

public class PartnerVipMoney {
    private String partnerId;

    private Integer partnerType;

    private Double vipConsumption;  //下级vip充值的总金额

    private Double firstVipConsumption; //事业合伙人专属收益部分的总金额

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
}
