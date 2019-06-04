package com.cqut.czb.bn.entity.dto.IndicatorRecord;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IndicatorRecordDTO{
    String recordId;

    String userId;

    String userAccount;

    Integer partner;

    Integer targetPromotionNumber;

    Integer actualPromotionNumber;

    Integer targetNewConsumer;

    Integer actualNewConsumer;

    Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date indicatorBeginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date indicatorEndTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

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

    public Integer getTargetPromotionNumber() {
        return targetPromotionNumber;
    }

    public void setTargetPromotionNumber(Integer targetPromotionNumber) {
        this.targetPromotionNumber = targetPromotionNumber;
    }

    public Integer getActualPromotionNumber() {
        return actualPromotionNumber;
    }

    public void setActualPromotionNumber(Integer actualPromotionNumber) {
        this.actualPromotionNumber = actualPromotionNumber;
    }

    public Integer getTargetNewConsumer() {
        return targetNewConsumer;
    }

    public void setTargetNewConsumer(Integer targetNewConsumer) {
        this.targetNewConsumer = targetNewConsumer;
    }

    public Integer getActualNewConsumer() {
        return actualNewConsumer;
    }

    public void setActualNewConsumer(Integer actualNewConsumer) {
        this.actualNewConsumer = actualNewConsumer;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getIndicatorBeginTime() {
        return indicatorBeginTime;
    }

    public void setIndicatorBeginTime(Date indicatorBeginTime) {
        this.indicatorBeginTime = indicatorBeginTime;
    }

    public Date getIndicatorEndTime() {
        return indicatorEndTime;
    }

    public void setIndicatorEndTime(Date indicatorEndTime) {
        this.indicatorEndTime = indicatorEndTime;
    }
}
