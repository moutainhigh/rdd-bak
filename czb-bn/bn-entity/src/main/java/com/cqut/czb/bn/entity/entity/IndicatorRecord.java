package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class IndicatorRecord {
    private String recordId;

    private Integer targetPromotionNumber;

    private Integer actualPromotionNumber;

    private Integer targetNewConsumer;

    private Integer actualNewConsumer;

    private Integer state;

    private Date indicatorBeginTime;

    private Date indicatorEndTime;

    private String userId;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
}