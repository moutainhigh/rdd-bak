package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import java.math.BigDecimal;

public class DirectAndIndirectDTO {
    //直推结算收益
    private BigDecimal direct;

    //间推结算收益
    private BigDecimal indirect;

    //vip直返收益
    private BigDecimal directVip;

    //所有vip直返
    private Double allDirectVipIncome;

    public Double getAllDirectVipIncome() {
        return allDirectVipIncome;
    }

    public void setAllDirectVipIncome(Double allDirectVipIncome) {
        this.allDirectVipIncome = allDirectVipIncome;
    }

    public BigDecimal getDirect() {
        return direct;
    }

    public void setDirect(BigDecimal direct) {
        this.direct = direct;
    }

    public BigDecimal getIndirect() {
        return indirect;
    }

    public void setIndirect(BigDecimal indirect) {
        this.indirect = indirect;
    }

    public BigDecimal getDirectVip() {
        return directVip;
    }

    public void setDirectVip(BigDecimal directVip) {
        this.directVip = directVip;
    }
}
