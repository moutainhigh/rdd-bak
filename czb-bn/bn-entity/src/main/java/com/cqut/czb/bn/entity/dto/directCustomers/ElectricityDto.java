package com.cqut.czb.bn.entity.dto.directCustomers;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ElectricityDto {
    private String appId;
    private String sign;
    private String orderId;
    private String rechargeAccount;
    private String regional;
    private Double amount;
    private String thirdAccount;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date time;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thridAccount) {
        this.thirdAccount = thridAccount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRechargeAccount() {
        return rechargeAccount;
    }

    public void setRechargeAccount(String rechargeAccount) {
        this.rechargeAccount = rechargeAccount;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public Date getCreateTime() {
        return time;
    }

    public void setCreateTime(Date createTime) {
        this.time = createTime;
    }

    @Override
    public String toString() {
        return "ElectricityDto{" +
                "appId='" + appId + '\'' +
                ", sign='" + sign + '\'' +
                ", orderId='" + orderId + '\'' +
                ", rechargeAccount='" + rechargeAccount + '\'' +
                ", regional='" + regional + '\'' +
                ", amount=" + amount +
                ", thirdAccount='" + thirdAccount + '\'' +
                ", time=" + time +
                '}';
    }
}
