package com.cqut.czb.bn.entity.dto.contractManagement;

import java.util.Date;

public class ContractDTO {

    private String contractId;

    private Date signedTime;

    private String signer;

    /**
     *  合同状态
     * */
    private Integer contractState; // 0: 未生效，1: 生效，2: 已失效

    /**
     *  签署状态
     * */
    private Integer signState; // 0: 未签署，1: 已签署

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getSignState() {
        return signState;
    }

    public void setSignState(Integer signState) {
        this.signState = signState;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }
}
