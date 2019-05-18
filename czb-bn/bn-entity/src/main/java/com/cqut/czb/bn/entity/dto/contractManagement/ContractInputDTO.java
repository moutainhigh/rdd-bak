package com.cqut.czb.bn.entity.dto.contractManagement;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContractInputDTO {

    private String contractId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signBeginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signEndTime;

    private String signer;

    private Integer contractState;

    private Integer contractType;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public Date getSignBeginTime() {
        return signBeginTime;
    }

    public void setSignBeginTime(Date signBeginTime) {
        this.signBeginTime = signBeginTime;
    }

    public Date getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Date signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }
}
