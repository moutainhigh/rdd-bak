package com.cqut.czb.bn.entity.dto.IndicatorRecord;

import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IndicatorRecordDTO{
    private String recordId;

    private String userId;

    private String userAccount;

    private Integer partner;

    private Integer targetPromotionNumber;

    private Integer actualPromotionNumber;

    private Integer targetNewConsumer;

    private Integer actualNewConsumer;

    private Integer state;

    private Integer isSpecial;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date indicatorBeginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date indicatorEndTime;

    private Date missionStartTime;

    private Date missionEndTime;

    private String recordIds;

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

    public Date getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(Date missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public Date getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(Date missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }
}
