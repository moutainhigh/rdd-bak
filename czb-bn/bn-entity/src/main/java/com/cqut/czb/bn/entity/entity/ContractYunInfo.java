package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class ContractYunInfo {
    private String yunContractId;

    private String userId;

    private String contractId;

    private String czId;

    private Date createAt;

    private Date updateAt;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}