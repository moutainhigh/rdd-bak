package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;

import java.util.Date;

public class WxSettleRcordDTO extends PageDTO  {
    private String recordId;        // 订单id

    private String settleUserId;    // 用户id

    private String userAccount;     // 用户名

    private String shopId;          // 店铺id

    private double totalAccount;   // 结算金额

    private int count;             // 订单数量

    private String settleTime;      // 结算时间

    private String createAt;        // 创建时间

    private String updateAt;       // 更新时间

    private String startTime;     // 开始时间

    private String endTime;       // 结束时间

    private int isSettlement;   // 结算状态

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

    public double getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(double totalAccount) {
        this.totalAccount = totalAccount;
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
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIsSettlement() {
        return isSettlement;
    }

    public void setIsSettlement(int isSettlement) {
        this.isSettlement = isSettlement;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
