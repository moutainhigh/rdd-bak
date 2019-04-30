package com.cqut.czb.bn.entity.entity;

public class ContractYunInfo {
    private String yunContractId;

    private String userId;

    private String contractId;

    private String czId;

    public String getYunContractId() {
        return yunContractId;
    }

    public void setYunContractId(String yunContractId) {
        this.yunContractId = yunContractId == null ? null : yunContractId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getCzId() {
        return czId;
    }

    public void setCzId(String czId) {
        this.czId = czId == null ? null : czId.trim();
    }
}