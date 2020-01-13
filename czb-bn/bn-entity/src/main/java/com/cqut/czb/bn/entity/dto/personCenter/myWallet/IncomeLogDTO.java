package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

public class IncomeLogDTO {
    /**
     * 收益变更记录id
     */
    private String recordId;

    /**
     * 用户
     */
    private String commissionGotUser;
    /**
     * 变更类型
     */
    private Integer type;

    /**
     * 变更金额
     */
    private double amount;

    /**
     * 用户收益信息表id
     */
    private String infoId;

    /**
     * 变更前金额
     */
    private double beforeChangeIncome;

    /**
     * 备注
     */
    private String remark;

    /**
     * 变更来源(支付宝订单号)
     */
    private String sourceId;

    /**
     * 提现到款账户
     */
    private String withdrawTo;

    /**
     * 提现人姓名
     */
    private String withdrawName;

    /**
     * 提现金额
     */
    private double withdrawAmount;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCommissionGotUser() {
        return commissionGotUser;
    }

    public void setCommissionGotUser(String commissionGotUser) {
        this.commissionGotUser = commissionGotUser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public double getBeforeChangeIncome() {
        return beforeChangeIncome;
    }

    public void setBeforeChangeIncome(double beforeChangeIncome) {
        this.beforeChangeIncome = beforeChangeIncome;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getWithdrawTo() {
        return withdrawTo;
    }

    public void setWithdrawTo(String withdrawTo) {
        this.withdrawTo = withdrawTo;
    }

    public String getWithdrawName() {
        return withdrawName;
    }

    public void setWithdrawName(String withdrawName) {
        this.withdrawName = withdrawName;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }
}
