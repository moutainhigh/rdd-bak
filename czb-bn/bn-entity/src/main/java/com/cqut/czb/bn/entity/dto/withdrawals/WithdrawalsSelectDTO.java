package com.cqut.czb.bn.entity.dto.withdrawals;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WithdrawalsSelectDTO extends PageDTO {
    private String userAccount;
    private String infoId;
    private String userName;
    private String amount;
    private String remark;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createAt;
    private String rest;
    private String currentRest;
    private String totalAmount;
    private String monthTotalAmount;
    private String todayTotalAmount;
    private String startTime;
    private String endTime;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrentRest() {
        return currentRest;
    }

    public void setCurrentRest(String currentRest) {
        this.currentRest = currentRest;
    }

    public String getMonthTotalAmount() {
        return monthTotalAmount;
    }

    public void setMonthTotalAmount(String monthTotalAmount) {
        this.monthTotalAmount = monthTotalAmount;
    }

    public String getTodayTotalAmount() {
        return todayTotalAmount;
    }

    public void setTodayTotalAmount(String todayTotalAmount) {
        this.todayTotalAmount = todayTotalAmount;
    }
}
