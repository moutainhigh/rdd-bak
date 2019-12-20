package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WeChatWithdrawDTO extends PageDTO {

    private String userId;

    private String infoId;

    private String account;

    private String name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createAt;

    private double withdrawed;

    private double withdraw;

    private String startTime;

    private String endTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWithdrawed() {
        return withdrawed;
    }

    public void setWithdrawed(double withdrawed) {
        this.withdrawed = withdrawed;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
