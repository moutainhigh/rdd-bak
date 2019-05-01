package com.cqut.czb.bn.entity.dto.rentCar;

import java.math.BigDecimal;

public class PersonalContractDTO {
    /**
     * 合同编号
     */
    private String contractId;

    /**
     * 车牌号
     */
    private String carLicense;

    /**
     * 合同结束时间
     */
    private String endTime;

    /**
     * 状态 0
     */
    private Integer status;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
