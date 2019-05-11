package com.cqut.czb.bn.entity.dto.rentCar;

public class CompanyContractInfoDTO {

    /**
     * 合同记录id
     */
    private String contractId;

    /**
     * 车辆数量
     */
    private String carNum;

    // 开始时间
    private String startTime;

    // 结束时间
    private String endTime;

    /**
     * 合同状态
     */
    private String status;

    /**
     * 合同已签租约数
     */
    private String isSignNum;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsSignNum() {
        return isSignNum;
    }

    public void setIsSignNum(String isSignNum) {
        this.isSignNum = isSignNum;
    }
}
