package com.cqut.czb.bn.entity.dto.rentCar;

import cn.afterturn.easypoi.entity.vo.BigExcelConstants;

import java.math.BigDecimal;

public class personIncome {
    private BigDecimal fanYongIncome;

    private BigDecimal shareIncome;

    private BigDecimal rentIncome;

    private BigDecimal otherIncome;

    private BigDecimal withDrawed;

    public BigDecimal getFanYongIncome() {
        return fanYongIncome;
    }

    public void setFanYongIncome(BigDecimal fanYongIncome) {
        this.fanYongIncome = fanYongIncome;
    }

    public BigDecimal getShareIncome() {
        return shareIncome;
    }

    public void setShareIncome(BigDecimal shareIncome) {
        this.shareIncome = shareIncome;
    }

    public BigDecimal getRentIncome() {
        return rentIncome;
    }

    public void setRentIncome(BigDecimal rentIncome) {
        this.rentIncome = rentIncome;
    }

    public BigDecimal getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(BigDecimal otherIncome) {
        this.otherIncome = otherIncome;
    }

    public BigDecimal getWithDrawed() {
        return withDrawed;
    }

    public void setWithDrawed(BigDecimal withDrawed) {
        this.withDrawed = withDrawed;
    }
}
