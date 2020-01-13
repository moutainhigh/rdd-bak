package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WeChatGetDetailedDTO extends PageDTO {

    private String infoId;

    private double amount;

    private double beforeChangeIncome;

    private int type;

    private String remark;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createAt;

    private String startTime;

    private String endTime;

    private String sourceUser;

    private String gotUser;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getGotUser() {
        return gotUser;
    }

    public void setGotUser(String gotUser) {
        this.gotUser = gotUser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
