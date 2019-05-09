package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

import java.util.List;

public class ContractIdListDTO {
    // 合同id列表
    private List<ContractIdInfo> contractIdLists;

    public ContractIdListDTO() {
    }

    public List<ContractIdInfo> getContractIdLists() {
        return contractIdLists;
    }

    public void setContractIdLists(List<ContractIdInfo> contractIdLists) {
        this.contractIdLists = contractIdLists;
    }
}
