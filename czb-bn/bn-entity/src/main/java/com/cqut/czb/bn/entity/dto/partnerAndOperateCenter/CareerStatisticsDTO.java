package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import java.math.BigDecimal;

public class CareerStatisticsDTO {
    //当前普通合伙人
    private Integer partnerCount;

    //当前年卡会员
    private Integer ordinaryUserCount;

    //直推vip人数
    private Integer directVipCount;

    //直推vip收益
    private BigDecimal directVipIncome;

    //直推油卡收益
    private BigDecimal directPetrolIncome;

    //间推vip人数
    private Integer indirectVipCount;

    //间推vip收益
    private BigDecimal indirectVipIncome;

    //间推油卡收益
    private BigDecimal indirectPetrolIncome;

    //已提现
    private BigDecimal withdrawed;

    //未提现
    private BigDecimal balance;

    //总收益
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
