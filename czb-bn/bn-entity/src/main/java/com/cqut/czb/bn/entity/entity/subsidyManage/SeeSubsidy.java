package com.cqut.czb.bn.entity.entity.subsidyManage;

public class SeeSubsidy {
    // 账号
    private String account;

    // 是否指定金额
    private Integer type;

    // 用户类型
    private Integer partner;

    // 补贴金额
    private Double amount;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
