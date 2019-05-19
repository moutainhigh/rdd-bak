package com.cqut.czb.bn.entity.dto.backOfEnterpriseContract;

public class PetrolInfoDTO {
    // 油卡记录id
    private String petrolRecordId;

    // 油卡号
    private  String petrolNum;

    // 买家
    private  String buyerId;

    // 支付方式
    private  Integer payMethod;

    // 第三方订单
    private String thirdNum;

    // 成交金额
    private Double amount;

    // 交易时间
    private String makeTime;

    // 油卡id
    private String petrolId;

    // 状态
    private Integer status;

    // 来源合同id
    private String contractId;

    // 油卡种类
    private Integer petrolType;

    public String getPetrolRecordId() {
        return petrolRecordId;
    }

    public void setPetrolRecordId(String petrolRecordId) {
        this.petrolRecordId = petrolRecordId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getThirdNum() {
        return thirdNum;
    }

    public void setThirdNum(String thirdNum) {
        this.thirdNum = thirdNum;
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

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }


    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }
}
