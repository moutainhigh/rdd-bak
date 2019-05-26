package com.cqut.czb.bn.entity.dto.backOfEnterpriseContract;

public class PetrolPayInfo {
    // 记录id
    private String recordId;

    // 支付方式
    private Integer payMethod;

    // 成交金额
    private Double amount;

    // 交易时间
    private String makeTime;

    // 交易状态
    private Integer status;

    // 是否充值到卡
    private Integer isCharged;

    // 油卡种类
    private Integer petrolType;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(String makeTime) {
        this.makeTime = makeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsCharged() {
        return isCharged;
    }

    public void setIsCharged(Integer isCharged) {
        this.isCharged = isCharged;
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }
}
