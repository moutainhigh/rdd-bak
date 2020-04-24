package com.cqut.czb.bn.entity.dto.petrolRecharge;

import com.cqut.czb.bn.entity.dto.PageDTO;

public class PetrolRechargeInputDTO extends PageDTO {
    private String userId;
    private String petrolNum;
    private String isRecharged;
    private String petrolKind;
    private String recordId;
    private String updatePetrolNum;
    private double petrolDenomination;
    private String recordType;
    private String userAccount;
    private Integer bindingType; //1 公司匹配 2 线下匹配 3 绑定过的卡
    private String startTime;
    private String endTime;
    private Integer rechargeState;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getRechargeState() {
        return rechargeState;
    }

    public void setRechargeState(Integer rechargeState) {
        this.rechargeState = rechargeState;
    }

    public Integer getBindingType() {
        return bindingType;
    }

    public void setBindingType(Integer bindingType) {
        this.bindingType = bindingType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public double getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(double petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public String getUpdatePetrolNum() {
        return updatePetrolNum;
    }

    public void setUpdatePetrolNum(String updatePetrolNum) {
        this.updatePetrolNum = updatePetrolNum;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getIsRecharged() {
        return isRecharged;
    }

    public void setIsRecharged(String isRecharged) {
        this.isRecharged = isRecharged;
    }

    public String getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(String petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
