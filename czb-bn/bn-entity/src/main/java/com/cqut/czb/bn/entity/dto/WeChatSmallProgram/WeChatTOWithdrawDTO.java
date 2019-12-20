package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class WeChatTOWithdrawDTO {

    private String userId;

    private String infoId;

    @NotNull(message = "提现金额不能为空")
    private BigDecimal money;

    private String paymentName;

    private String paymentAccount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }
}
