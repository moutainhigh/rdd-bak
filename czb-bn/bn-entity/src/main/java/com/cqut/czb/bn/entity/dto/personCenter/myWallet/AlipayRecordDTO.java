package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AlipayRecordDTO {
    /**
     * 支付宝账号
     */
    private String paymentAccount;

    /**
     * 支付宝用户真实姓名
     */
    private String paymentName;

    /**
     * 提现金额
     */
    private BigDecimal paymentAmount;

    private String keyWord;

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
