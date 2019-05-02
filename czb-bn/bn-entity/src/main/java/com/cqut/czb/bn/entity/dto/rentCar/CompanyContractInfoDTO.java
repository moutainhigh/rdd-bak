package com.cqut.czb.bn.entity.dto.rentCar;

public class CompanyContractInfoDTO {

    /**
     * 第三方合同编号
     */
    private String contractId;

    /**
     * 车辆数量
     */
    private Integer carNum;

    /**
     * 合同状态
     */
    private Integer status;

    /**
     * 合同已签租约数
     */
    private Integer isSignNum;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsSignNum() {
        return isSignNum;
    }

    public void setIsSignNum(Integer isSignNum) {
        this.isSignNum = isSignNum;
    }
}
