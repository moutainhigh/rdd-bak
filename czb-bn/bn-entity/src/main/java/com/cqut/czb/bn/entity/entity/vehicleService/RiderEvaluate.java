package com.cqut.czb.bn.entity.entity.vehicleService;

import java.util.Date;

public class RiderEvaluate {
    private String evaluateId;

    private String evaluateRiderId;

    private Float evaluateLevel;

    private String evaluateMessage;

    private Date createAt;

    private Date updateAt;

    private String evaluateUserId;

    public String getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(String evaluateId) {
        this.evaluateId = evaluateId == null ? null : evaluateId.trim();
    }

    public String getEvaluateRiderId() {
        return evaluateRiderId;
    }

    public void setEvaluateRiderId(String evaluateRiderId) {
        this.evaluateRiderId = evaluateRiderId == null ? null : evaluateRiderId.trim();
    }

    public Float getEvaluateLevel() {
        return evaluateLevel;
    }

    public void setEvaluateLevel(Float evaluateLevel) {
        this.evaluateLevel = evaluateLevel;
    }

    public String getEvaluateMessage() {
        return evaluateMessage;
    }

    public void setEvaluateMessage(String evaluateMessage) {
        this.evaluateMessage = evaluateMessage == null ? null : evaluateMessage.trim();
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

    public String getEvaluateUserId() {
        return evaluateUserId;
    }

    public void setEvaluateUserId(String evaluateUserId) {
        this.evaluateUserId = evaluateUserId == null ? null : evaluateUserId.trim();
    }
}