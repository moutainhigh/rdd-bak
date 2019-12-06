package com.cqut.czb.bn.entity.entity.weChatSmallProgram;

import java.util.Date;

public class WechatSettleRecord {
    private String recordId;

    private String settleUserId;

    private String settledUserId;

    private String shopId;

    private Double totalAmount;

    private Date settleTime;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getSettleUserId() {
        return settleUserId;
    }

    public void setSettleUserId(String settleUserId) {
        this.settleUserId = settleUserId == null ? null : settleUserId.trim();
    }

    public String getSettledUserId() {
        return settledUserId;
    }

    public void setSettledUserId(String settledUserId) {
        this.settledUserId = settledUserId == null ? null : settledUserId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
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