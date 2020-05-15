package com.cqut.czb.bn.entity.dto;

import com.github.pagehelper.PageInfo;

public class OfflineRecordsDTO {
    private PageInfo OfflineRecordsListDTOList;

    private Double totalSale;

    private Double totalRecharge;

    public PageInfo getOfflineRecordsListDTOList() {
        return OfflineRecordsListDTOList;
    }

    public void setOfflineRecordsListDTOList(PageInfo offlineRecordsListDTOList) {
        OfflineRecordsListDTOList = offlineRecordsListDTOList;
    }

    public Double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Double totalSale) {
        this.totalSale = totalSale;
    }

    public Double getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(Double totalRecharge) {
        this.totalRecharge = totalRecharge;
    }
}

