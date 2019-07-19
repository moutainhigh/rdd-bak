package com.cqut.czb.bn.entity.dto.appVIP;

import com.cqut.czb.bn.entity.dto.PageDTO;

public class VipRechargeRecordDTO extends PageDTO {

    private String recordId;

    private String userId;

    private Double amount;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
