package com.cqut.czb.bn.entity.dto.vehicleService;

import java.util.Date;

public class RiderEvaluateDTO {
    private String evaluateId;

    private String evaluateRiderId;

    private Float evaluateLevel;

    private String evaluateMessage;

    private Date createAt;

    private Date updateAt;

    private String evaluateUserId;

    private String userAccount;

    private String riderName;

    public String getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(String evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getEvaluateRiderId() {
        return evaluateRiderId;
    }

    public void setEvaluateRiderId(String evaluateRiderId) {
        this.evaluateRiderId = evaluateRiderId;
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
        this.evaluateMessage = evaluateMessage;
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
        this.evaluateUserId = evaluateUserId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }
}
