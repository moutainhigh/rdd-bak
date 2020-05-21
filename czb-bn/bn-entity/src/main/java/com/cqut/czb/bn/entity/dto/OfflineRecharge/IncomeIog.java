package com.cqut.czb.bn.entity.dto.OfflineRecharge;

public class IncomeIog {
    private String recordId;

    private double amount;

    private int type;

    private String infoId;

    private double beforeChangeIncome;

    private String remark;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
