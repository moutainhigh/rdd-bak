package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import java.math.BigDecimal;

/**
 * 余额记录
 * @param
 * @return
 */
public class WeChatBalanceRecord {

    /**
     * 余额记录id
     */
    private String infoId;

    /**
     * 余额
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
