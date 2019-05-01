package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AlipayRecordDTO {
    /**
     * 支付宝账号
     */
    @NotNull(message = "支付宝账号不能为空")
    private String paymentAccount;

    /**
     * 支付宝用户真实姓名
     */
    @NotNull(message = "用户真实姓名不能为空")
    private String paymentName;

    /**
     * 提现金额
     */
    @NotNull(message = "提现金额不能为空")
    private BigDecimal paymentAmount;

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
}
