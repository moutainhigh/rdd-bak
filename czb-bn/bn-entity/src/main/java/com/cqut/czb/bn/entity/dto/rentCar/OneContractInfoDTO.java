package com.cqut.czb.bn.entity.dto.rentCar;

import java.math.BigDecimal;

public class OneContractInfoDTO {
    /**
     * 合同编号
     */
    private String contractId;

    /**
     * 车牌号
     */
    private String carLicense;

    /**
     * 合同开始时间
     */
    private String startTime;

    /**
     * 合同结束时间
     */
    private String endTime;

    /**
     * 租金
     */
    private BigDecimal rentMoney;

    /**
     * 状态 0
     */
    private Integer status;

    /**
     * 认证码 0 在租 1失效 2未提交
     */
    private String identifyCode;

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

    public BigDecimal getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(BigDecimal rentMoney) {
        this.rentMoney = rentMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }
}
