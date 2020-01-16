package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import java.util.Date;
import java.text.SimpleDateFormat;

public class WeChatSettleRecord {
    private String recordId;
    private  String settleUserId;
    private String shopId;
    private Double totalAmount;
    private String settleTime;
    private String createAt;
    private String updateAt;
    private int isSettlement;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSettleUserId() {
        return settleUserId;
    }

    public void setSettleUserId(String settleUserId) {
        this.settleUserId = settleUserId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return format(new Date());
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getIsSettlement() {
        return isSettlement;
    }

    public void setIsSettlement(int isSettlement) {
        this.isSettlement = isSettlement;
    }
    public String format(Date date) {
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf3.format(date);
    }
}
