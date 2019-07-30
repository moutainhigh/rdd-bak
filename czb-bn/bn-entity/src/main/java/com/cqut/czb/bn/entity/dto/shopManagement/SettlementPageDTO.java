package com.cqut.czb.bn.entity.dto.shopManagement;

import com.github.pagehelper.PageInfo;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-29 17:38
 */
public class SettlementPageDTO {
    private PageInfo settlementDTOList;

    private Double totalUnsettledAmount;

    public PageInfo getSettlementDTOList() {
        return settlementDTOList;
    }

    public void setSettlementDTOList(PageInfo settlementDTOList) {
        this.settlementDTOList = settlementDTOList;
    }

    public Double getTotalUnsettledAmount() {
        return totalUnsettledAmount;
    }

    public void setTotalUnsettledAmount(Double totalUnsettledAmount) {
        this.totalUnsettledAmount = totalUnsettledAmount;
    }
}