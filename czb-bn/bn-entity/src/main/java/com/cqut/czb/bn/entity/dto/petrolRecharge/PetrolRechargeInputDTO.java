package com.cqut.czb.bn.entity.dto.petrolRecharge;

import com.cqut.czb.bn.entity.dto.PageDTO;

public class PetrolRechargeInputDTO extends PageDTO {
    private String petrolNum;
    private String isRecharged;
    private String petrolKind;

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
