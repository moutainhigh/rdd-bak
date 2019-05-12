package com.cqut.czb.bn.entity.dto.appPersonalCenter;

public class PetrolInfoDTO {

    private  Integer petrolKind;//油卡类型

    private String petrolName;//油卡名称

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getPetrolName() {
        return petrolName;
    }

    public void setPetrolName(String petrolName) {
        this.petrolName = petrolName;
    }
}
