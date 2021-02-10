package com.cqut.czb.bn.entity.dto.integral;

import java.util.Date;

public class IntegralDetailsDTO {
    private String integralExchange;

    private String userId;

    private Integer integralLogType;

    private Integer integralAmount;

    private String orderId;

    private String remark;

    private Integer exchangeType;

    private Date createAt;

    private Date updateAt;

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getIntegralExchange() {
        return integralExchange;
    }

    public void setIntegralExchange(String integralExchange) {
        this.integralExchange = integralExchange;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIntegralLogType() {
        return integralLogType;
    }

    public void setIntegralLogType(Integer integralLogType) {
        this.integralLogType = integralLogType;
    }

    public Integer getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(Integer integralAmount) {
        this.integralAmount = integralAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
