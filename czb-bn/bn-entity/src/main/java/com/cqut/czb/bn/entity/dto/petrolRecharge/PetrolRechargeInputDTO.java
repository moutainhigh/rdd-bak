package com.cqut.czb.bn.entity.dto.petrolRecharge;

import com.cqut.czb.bn.entity.dto.PageDTO;

public class PetrolRechargeInputDTO extends PageDTO {
    private String petrolNum;
    private String isRecharged;
    private String petrolKind;
    private String recordId;
    private String updatePetrolNum;
    private double petrolDenomination;

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
}
