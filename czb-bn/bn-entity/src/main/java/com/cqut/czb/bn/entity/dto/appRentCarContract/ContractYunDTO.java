package com.cqut.czb.bn.entity.dto.appRentCarContract;

public class ContractYunDTO {
    /**
     * 云合同信息表id
     */
    private String YunContractId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 存证id
     */
    private String czId;

    public String getYunContractId() {
        return YunContractId;
    }

    public void setYunContractId(String yunContractId) {
        YunContractId = yunContractId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCzId() {
        return czId;
    }

    public void setCzId(String czId) {
        this.czId = czId;
    }
}
