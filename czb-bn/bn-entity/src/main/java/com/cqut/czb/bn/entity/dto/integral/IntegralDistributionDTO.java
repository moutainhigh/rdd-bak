package com.cqut.czb.bn.entity.dto.integral;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;

import java.util.Date;

public class IntegralDistributionDTO extends PageDTO {
    private Integer integralAmount;

    private String userAccount;

    private Integer type;

    private Date createAt;

    private String IntegralExchange;

    private String exchangeCode;

    public Integer getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(Integer integralAmount) {
        this.integralAmount = integralAmount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getIntegralExchange() {
        return IntegralExchange;
    }

    public void setIntegralExchange(String integralExchange) {
        IntegralExchange = integralExchange;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }
}
