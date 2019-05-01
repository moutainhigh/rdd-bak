package com.cqut.czb.bn.entity.dto.rentCar;

import javax.validation.constraints.NotNull;

public class OneContractInfoInputDTO {
    @NotNull(message = "合同id不能为空")
    private String contractId;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
