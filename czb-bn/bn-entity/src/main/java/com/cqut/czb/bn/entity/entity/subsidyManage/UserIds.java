package com.cqut.czb.bn.entity.entity.subsidyManage;

public class UserIds {
    // 补贴比例
    private Double subsidyRent;

    // 补贴月份
    private String subsidyMonth;

    // 用户ids
    private String userIdS;

    // 指定金额
    private Double subsidyMoney;

    public Double getSubsidyMoney() {
        return subsidyMoney;
    }

    public void setSubsidyMoney(Double subsidyMoney) {
        this.subsidyMoney = subsidyMoney;
    }

    public String getUserIdS() {
        return userIdS;
    }

    public void setUserIdS(String userIdS) {
        this.userIdS = userIdS;
    }

    public Double getSubsidyRent() {
        return subsidyRent;
    }

    public void setSubsidyRent(Double subsidyRent) {
        this.subsidyRent = subsidyRent;
    }

    public String getSubsidyMonth() {
        return subsidyMonth;
    }

    public void setSubsidyMonth(String subsidyMonth) {
        this.subsidyMonth = subsidyMonth;
    }
}
