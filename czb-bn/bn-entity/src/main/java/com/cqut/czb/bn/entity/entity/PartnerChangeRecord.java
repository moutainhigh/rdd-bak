package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class PartnerChangeRecord {
    private String recordId;

    private String userId;

    private Integer beforeChangeLevel;

    private Integer afterChangeLevel;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getBeforeChangeLevel() {
        return beforeChangeLevel;
    }

    public void setBeforeChangeLevel(Integer beforeChangeLevel) {
        this.beforeChangeLevel = beforeChangeLevel;
    }

    public Integer getAfterChangeLevel() {
        return afterChangeLevel;
    }

    public void setAfterChangeLevel(Integer afterChangeLevel) {
        this.afterChangeLevel = afterChangeLevel;
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