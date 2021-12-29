package com.cqut.czb.bn.entity.dto.withdrawals;

import com.cqut.czb.bn.util.string.StringUtil;

public class WithdrawalsInsertDTO {
    private final String recodeId = StringUtil.createId();
    private String amount;
    private String type = "9";
    private String infoId;
    private String beforeChangeIncome;
    private String remark = "囧途红包";
    private final String souseId = StringUtil.createId();

    public String getRecodeId() {
        return recodeId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getBeforeChangeIncome() {
        return beforeChangeIncome;
    }

    public void setBeforeChangeIncome(String beforeChangeIncome) {
        this.beforeChangeIncome = beforeChangeIncome;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSouseId() {
        return souseId;
    }
}
