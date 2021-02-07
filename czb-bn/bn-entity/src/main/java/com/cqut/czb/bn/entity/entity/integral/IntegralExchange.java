package com.cqut.czb.bn.entity.entity.integral;

import java.util.Date;

public class IntegralExchange {
    private String integralExchange;

    private String exchangeCode;

    private Integer exchangeAmount;

    private Integer exchangeTimesTotal;

    private Integer exchangeTimesCurrent;

    private Integer exchangeType;

    private String exchangeSourceId;

    private Integer isComplete;

    private Date failureTime;

    private Date createAt;

    private Date updateAt;

    public String getIntegralExchange() {
        return integralExchange;
    }

    public void setIntegralExchange(String integralExchange) {
        this.integralExchange = integralExchange == null ? null : integralExchange.trim();
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode == null ? null : exchangeCode.trim();
    }

    public Integer getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(Integer exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public Integer getExchangeTimesTotal() {
        return exchangeTimesTotal;
    }

    public void setExchangeTimesTotal(Integer exchangeTimesTotal) {
        this.exchangeTimesTotal = exchangeTimesTotal;
    }

    public Integer getExchangeTimesCurrent() {
        return exchangeTimesCurrent;
    }

    public void setExchangeTimesCurrent(Integer exchangeTimesCurrent) {
        this.exchangeTimesCurrent = exchangeTimesCurrent;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getExchangeSourceId() {
        return exchangeSourceId;
    }

    public void setExchangeSourceId(String exchangeSourceId) {
        this.exchangeSourceId = exchangeSourceId == null ? null : exchangeSourceId.trim();
    }

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
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
