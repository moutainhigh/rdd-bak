package com.cqut.czb.bn.entity.entity.integral;

import java.util.Date;

public class IntegralExchangeLogId {
    private String integralExchangeLogId;

    private String integralExchangeId;

    private String exchangeUserId;

    private Date createAt;

    private Date updateAt;

    public String getIntegralExchangeLogId() {
        return integralExchangeLogId;
    }

    public void setIntegralExchangeLogId(String integralExchangeLogId) {
        this.integralExchangeLogId = integralExchangeLogId == null ? null : integralExchangeLogId.trim();
    }

    public String getIntegralExchangeId() {
        return integralExchangeId;
    }

    public void setIntegralExchangeId(String integralExchangeId) {
        this.integralExchangeId = integralExchangeId == null ? null : integralExchangeId.trim();
    }

    public String getExchangeUserId() {
        return exchangeUserId;
    }

    public void setExchangeUserId(String exchangeUserId) {
        this.exchangeUserId = exchangeUserId;
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
