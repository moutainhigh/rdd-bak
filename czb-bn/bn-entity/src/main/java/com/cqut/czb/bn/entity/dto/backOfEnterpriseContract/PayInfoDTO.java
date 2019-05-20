package com.cqut.czb.bn.entity.dto.backOfEnterpriseContract;

public class PayInfoDTO {
    // 打款记录id
    private String payId;

    // 收款人姓名
    private String payToName;

    // 收款人身份证
    private String PersonCarId;

    // 合同记录id
    private String contractId;

    // 应付金额
    private Double shouldPay;

    // 实收金额
    private Double realPay;

    // 目标年月
    private String yearMonth;

    // 打款日期
    private String payTime;

    // 状态
    private Integer status;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayToName() {
        return payToName;
    }

    public void setPayToName(String payToName) {
        this.payToName = payToName;
    }

    public String getPersonCarId() {
        return PersonCarId;
    }

    public void setPersonCarId(String personCarId) {
        PersonCarId = personCarId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Double getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(Double shouldPay) {
        this.shouldPay = shouldPay;
    }

    public Double getRealPay() {
        return realPay;
    }

    public void setRealPay(Double realPay) {
        this.realPay = realPay;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
