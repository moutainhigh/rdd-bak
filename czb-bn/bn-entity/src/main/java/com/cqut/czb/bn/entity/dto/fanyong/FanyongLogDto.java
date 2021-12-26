package com.cqut.czb.bn.entity.dto.fanyong;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FanyongLogDto extends PageDTO {
    private String sourceUser;
    private String gotUser;
    private String amount;
    private String souseId;
    private String remark;
    private String beforeAmount;
    private String account;
    //创建时间
    private Date createAt;
    //更新时间
    private Date updateAt;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getSouseId() {
        return souseId;
    }

    public void setSouseId(String souseId) {
        this.souseId = souseId;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
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

    public String getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(String beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "FanyongLogDto{" +
                "sourceUser='" + sourceUser + '\'' +
                ", gotUser='" + gotUser + '\'' +
                ", amount='" + amount + '\'' +
                ", souseId='" + souseId + '\'' +
                ", remark='" + remark + '\'' +
                ", beforeAmount='" + beforeAmount + '\'' +
                ", account='" + account + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
