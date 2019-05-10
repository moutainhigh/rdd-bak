package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

import java.math.BigDecimal;

public class BalanceAndInfoIdDTO {

    /**
     * 余额记录id
     */
    private String infoId;

    /**
     * 余额
     */
    private BigDecimal balance;

    public BalanceAndInfoIdDTO() {
    }

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
