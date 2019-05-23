package com.cqut.czb.bn.entity.dto.payToPerson;

import java.util.Date;

public class PayToPersonDTO {
    private String recordId;
    //收款人
    private String payeeName;
    //开户行
    private String bankOfDeposit;
    //银行账号
    private String bankAccountNum;
    //身份证号
    private String payeeIdCard;
    //合同id
    private String contractRecordId;
    //应打款
    private Double payableMoney;
    //实打款
    private Double actualPayMoney;
    //目标月
    private Date targetYearMonth;
    //实际打款时间
    private Date platformPayTime;
    //状态 0 未打款 1 已打款 2 打款异常
    private Integer state;
     //是否删除 0 未删除 1 已删除
    private Integer isDeleted;
    //备注
    private String remark;

    //导出时的选择时间（年+月）
    private String exportTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName == null ? null : payeeName.trim();
    }

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }

    public String getBankAccountNum() {
        return bankAccountNum;
    }

    public void setBankAccountNum(String bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }

    public String getPayeeIdCard() {
        return payeeIdCard;
    }

    public void setPayeeIdCard(String payeeIdCard) {
        this.payeeIdCard = payeeIdCard == null ? null : payeeIdCard.trim();
    }

    public String getContractRecordId() {
        return contractRecordId;
    }

    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId == null ? null : contractRecordId.trim();
    }

    public Double getPayableMoney() {
        return payableMoney;
    }

    public void setPayableMoney(Double payableMoney) {
        this.payableMoney = payableMoney;
    }

    public Double getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(Double actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    public Date getTargetYearMonth() {
        return targetYearMonth;
    }

    public void setTargetYearMonth(Date targetYearMonth) {
        this.targetYearMonth = targetYearMonth;
    }

    public Date getPlatformPayTime() {
        return platformPayTime;
    }

    public void setPlatformPayTime(Date platformPayTime) {
        this.platformPayTime = platformPayTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExportTime() {
        return exportTime;
    }

    public void setExportTime(String exportTime) {
        this.exportTime = exportTime;
    }
}
