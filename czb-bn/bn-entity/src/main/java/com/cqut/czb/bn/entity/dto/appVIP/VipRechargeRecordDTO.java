package com.cqut.czb.bn.entity.dto.appVIP;

public class VipRechargeRecordDTO {

    private String recordId;

    private String userId;

    private Double account;

    private Integer rechargeWay;

    private Integer isReceived;

    private String thirdTradeNum;

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

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Integer getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(Integer rechargeWay) {
        this.rechargeWay = rechargeWay;
    }

    public Integer getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Integer isReceived) {
        this.isReceived = isReceived;
    }

    public String getThirdTradeNum() {
        return thirdTradeNum;
    }

    public void setThirdTradeNum(String thirdTradeNum) {
        this.thirdTradeNum = thirdTradeNum;
    }
}
