package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

import javax.validation.constraints.NotNull;

public class ContractIdInfo {
    // 合同记录id
    @NotNull(message = "合同记录id不能为空")
    private String contractId;

    public ContractIdInfo() {
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
