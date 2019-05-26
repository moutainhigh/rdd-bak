package com.cqut.czb.bn.entity.dto.backOfEnterpriseContract;

import javax.validation.constraints.NotNull;

public class PetrolIdInfo {
    // 油卡id
    @NotNull(message = "油卡id不能为空")
    private String petrolId;

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }
}
