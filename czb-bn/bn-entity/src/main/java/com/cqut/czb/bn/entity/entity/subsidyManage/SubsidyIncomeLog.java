package com.cqut.czb.bn.entity.entity.subsidyManage;

import com.cqut.czb.bn.util.string.StringUtil;

public class SubsidyIncomeLog {
    // 记录id
    private String recordId;

    // 变更金额
    private Double amount;

    // 变更类型
    private Integer type;

    // 收益id
    private String infoId;

    @Override
    public String toString() {
        return "SubsidyIncomeLog{" +
                "recordId='" + recordId + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", infoId='" + infoId + '\'' +
                ", remark='" + remark + '\'' +
                ", targetMonth='" + targetMonth + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", beforeMoney=" + beforeMoney +
                '}';
    }

    // 备注
    private String remark;

    // 目标年月
    private String targetMonth;

    // 变更来源
    private String sourceId;

    // 变更前金额
    private Double beforeMoney;

    public SubsidyIncomeLog(){
        this(StringUtil.createId(), 4, "补贴");
    }

    public SubsidyIncomeLog(String recordId, Integer type, String remark){
        this.recordId = recordId;
        this.type = type;
        this.remark = remark;
    }

    public Double getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(String targetMonth) {
        this.targetMonth = targetMonth;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
