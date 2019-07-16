package com.cqut.czb.bn.entity.dto.CreateSubsidies;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CreateSubsidiesQueryDTO extends PageDTO {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 使用类型（是否是合伙人 0 不是，1 普通合伙人，2 事业合伙人）
     */
    private Integer partner;

    /**
     * 查询最小字段开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 查询最小字段结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 最小消费金额
     */
    private Double minConsumptionAmount;

    /**
     * 最小消费次数
     */
    private Integer minConsumptionTimes;

    /**
     * 最小推荐人个数
     */
    private Integer minConsumersNumber;

    /**
     * 预补贴月份
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date subsidyMonth;

    /**
     * 每月消费消费金额
     */
    private Double monthlyEarnings;

    /**
     * 预获得补贴金额
     */
    private Double subsidies;

    /**
     * 预补贴利率
     */
    private Double subsidyRent;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getMinConsumptionAmount() {
        return minConsumptionAmount;
    }

    public void setMinConsumptionAmount(Double minConsumptionAmount) {
        this.minConsumptionAmount = minConsumptionAmount;
    }

    public Integer getMinConsumptionTimes() {
        return minConsumptionTimes;
    }

    public void setMinConsumptionTimes(Integer minConsumptionTimes) {
        this.minConsumptionTimes = minConsumptionTimes;
    }

    public Integer getMinConsumersNumber() {
        return minConsumersNumber;
    }

    public void setMinConsumersNumber(Integer minConsumersNumber) {
        this.minConsumersNumber = minConsumersNumber;
    }

    public Date getSubsidyMonth() {
        return subsidyMonth;
    }

    public void setSubsidyMonth(Date subsidyMonth) {
        this.subsidyMonth = subsidyMonth;
    }

    public Double getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public void setMonthlyEarnings(Double monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
    }

    public Double getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(Double subsidies) {
        this.subsidies = subsidies;
    }

    public Double getSubsidyRent() {
        return subsidyRent;
    }

    public void setSubsidyRent(Double subsidyRent) {
        this.subsidyRent = subsidyRent;
    }
}
