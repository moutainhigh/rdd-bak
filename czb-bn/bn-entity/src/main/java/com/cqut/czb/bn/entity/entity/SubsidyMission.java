package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class SubsidyMission {
    private String missionId;

    private Date targetYearMonth;

    private Double subsidyPercent;

    private Integer state;

    private String partner;

    private Date createAt;

    private Date updateAt;

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId == null ? null : missionId.trim();
    }

    public Date getTargetYearMonth() {
        return targetYearMonth;
    }

    public void setTargetYearMonth(Date targetYearMonth) {
        this.targetYearMonth = targetYearMonth;
    }

    public Double getSubsidyPercent() {
        return subsidyPercent;
    }

    public void setSubsidyPercent(Double subsidyPercent) {
        this.subsidyPercent = subsidyPercent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
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