package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

import java.math.BigDecimal;

public class BalanceAndInfoIdDTO {

    /**
     * 用户id
     */
    private String infoId;

    /**
     * 角色类型
     */
    private BigDecimal balance;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
