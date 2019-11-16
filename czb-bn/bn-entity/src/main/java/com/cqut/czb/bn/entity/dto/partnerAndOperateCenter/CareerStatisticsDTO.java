package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import java.math.BigDecimal;

public class CareerStatisticsDTO {
    private Integer partnerCount;

    private Integer ordinaryUserCount;

    private Integer directVipCount;

    private BigDecimal directVipIncome;

    private BigDecimal directPetrolIncome;

    private Integer indirectVipCount;

    private BigDecimal indirectVipIncome;

    private BigDecimal indirectPetrolIncome;

    private BigDecimal withdrawed;

    private BigDecimal balance;

    private BigDecimal allCommissionIncome;

    public Integer getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(Integer partnerCount) {
        this.partnerCount = partnerCount;
    }

    public Integer getOrdinaryUserCount() {
        return ordinaryUserCount;
    }

    public void setOrdinaryUserCount(Integer ordinaryUserCount) {
        this.ordinaryUserCount = ordinaryUserCount;
    }

    public Integer getDirectVipCount() {
        return directVipCount;
    }

    public void setDirectVipCount(Integer directVipCount) {
        this.directVipCount = directVipCount;
    }

    public BigDecimal getDirectVipIncome() {
        return directVipIncome;
    }

    public void setDirectVipIncome(BigDecimal directVipIncome) {
        this.directVipIncome = directVipIncome;
    }

    public BigDecimal getDirectPetrolIncome() {
        return directPetrolIncome;
    }

    public void setDirectPetrolIncome(BigDecimal directPetrolIncome) {
        this.directPetrolIncome = directPetrolIncome;
    }

    public Integer getIndirectVipCount() {
        return indirectVipCount;
    }

    public void setIndirectVipCount(Integer indirectVipCount) {
        this.indirectVipCount = indirectVipCount;
    }

    public BigDecimal getIndirectVipIncome() {
        return indirectVipIncome;
    }

    public void setIndirectVipIncome(BigDecimal indirectVipIncome) {
        this.indirectVipIncome = indirectVipIncome;
    }

    public BigDecimal getIndirectPetrolIncome() {
        return indirectPetrolIncome;
    }

    public void setIndirectPetrolIncome(BigDecimal indirectPetrolIncome) {
        this.indirectPetrolIncome = indirectPetrolIncome;
    }

    public BigDecimal getWithdrawed() {
        return withdrawed;
    }

    public void setWithdrawed(BigDecimal withdrawed) {
        this.withdrawed = withdrawed;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAllCommissionIncome() {
        return allCommissionIncome;
    }

    public void setAllCommissionIncome(BigDecimal allCommissionIncome) {
        this.allCommissionIncome = allCommissionIncome;
    }
}
