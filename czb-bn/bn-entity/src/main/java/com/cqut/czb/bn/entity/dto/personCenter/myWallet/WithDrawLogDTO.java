package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

public class WithDrawLogDTO {
    // 提现金额
    private Double money;

    // 年月
    private String yearMonth;

    // 时间
    private String createTime;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
