package com.cqut.czb.bn.entity.dto.rentCar.personContractSigned;

public class SignerIdAndContractId {
    // 云用户id
    private String signerId;

    // 第三方云合同id
    private String contractId;

    // 云token
    private String token;

    public String getSignerId() {
        return signerId;
    }

    public void setSignerId(String signerId) {
        this.signerId = signerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
