package com.cqut.czb.bn.entity.dto.VIPRechargeRecord;


import com.github.pagehelper.PageInfo;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-20 14:15
 */
public class VipRechargeRecordDTO {
    private PageInfo vipRechargeRecordListDTOList;

    private Double totalAmount;

    public void setVipRechargeRecordListDTOList(PageInfo vipRechargeRecordListDTOList) {
        this.vipRechargeRecordListDTOList = vipRechargeRecordListDTOList;
    }

    public PageInfo getVipRechargeRecordListDTOList() {
        return vipRechargeRecordListDTOList;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}