package com.cqut.czb.bn.entity.dto.rentCar;

public class ContractAndSignedNumDTO {
    /**
     * 企业第三方合同编号
     */
    private String contractId;

    /**
     * 个人已签订数
     */
    private Integer signedCarNum;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getSignedCarNum() {
        return signedCarNum;
    }

    public void setSignedCarNum(Integer signedCarNum) {
        this.signedCarNum = signedCarNum;
    }
}
