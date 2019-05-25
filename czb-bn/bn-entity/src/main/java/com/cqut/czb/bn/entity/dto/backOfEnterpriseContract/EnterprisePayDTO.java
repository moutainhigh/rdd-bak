package com.cqut.czb.bn.entity.dto.backOfEnterpriseContract;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EnterprisePayDTO {
    private String personName;

    private String contractRecordId;

    private Double receivableMoney;

    private Double actualReceiptsMoney;
    //目标月
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date targetYearMonth;
    //企业实际打款时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterprisePayTime;
    //状态 0 未打款 1 已打款 2 打款异常
    private Integer state;
    //备注
    private String remark;

    private Double totalMoney;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getContractRecordId() {
        return contractRecordId;
    }

    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId;
    }

    public Double getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(Double receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public Double getActualReceiptsMoney() {
        return actualReceiptsMoney;
    }

    public void setActualReceiptsMoney(Double actualReceiptsMoney) {
        this.actualReceiptsMoney = actualReceiptsMoney;
    }

    public Date getTargetYearMonth() {
        return targetYearMonth;
    }

    public void setTargetYearMonth(Date targetYearMonth) {
        this.targetYearMonth = targetYearMonth;
    }

    public Date getEnterprisePayTime() {
        return enterprisePayTime;
    }

    public void setEnterprisePayTime(Date enterprisePayTime) {
        this.enterprisePayTime = enterprisePayTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
}
