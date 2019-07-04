package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class CommodityUsageRecord {
    private String recordId;

    private String orderId;

    private Date usageTime;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Date usageTime) {
        this.usageTime = usageTime;
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