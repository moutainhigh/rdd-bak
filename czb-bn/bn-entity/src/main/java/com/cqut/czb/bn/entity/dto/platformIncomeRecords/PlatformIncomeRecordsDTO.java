package com.cqut.czb.bn.entity.dto.platformIncomeRecords;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PlatformIncomeRecordsDTO {
    private String recordId;
    //合同id
    private String contractRecordId;
    //企业名
    private String userName;
    //应收款
    private Double receivableMoney;
    //实收款
    private Double actualReceiptsMoney;
    //目标月
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date targetYearMonth;
    //企业实际打款时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterprisePayTime;
    //状态 0 未打款 1 已打款 2 打款异常
    private Integer state;
    //是否删除 0 未删除 1 已删除
    private Integer isDeleted;
    //是否需要生成充值记录
    private Integer isNeedRecharge;
    //是否分配油卡
    private Integer isDistributed;
    //导出时所选时间
    private String exportTime;
    //备注
    private String remark;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getContractRecordId() {
        return contractRecordId;
    }

    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId == null ? null : contractRecordId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsNeedRecharge() {
        return isNeedRecharge;
    }

    public void setIsNeedRecharge(Integer isNeedRecharge) {
        this.isNeedRecharge = isNeedRecharge;
    }

    public Integer getIsDistributed() {
        return isDistributed;
    }

    public void setIsDistributed(Integer isDistributed) {
        this.isDistributed = isDistributed;
    }

    public String getExportTime() {
        return exportTime;
    }

    public void setExportTime(String exportTime) {
        this.exportTime = exportTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
