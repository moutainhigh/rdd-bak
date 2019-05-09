package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

import javax.validation.constraints.NotNull;

public class CompanySignedTime {
    // 合同开始时间
    @NotNull(message = "开始时间不能为空")
    public String startTime;

    // 合同记录id
    @NotNull(message = "合同记录id不能为空")
    public String contractId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
