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

    private Double todayTotalAmount;

    private Integer  todayTotalNum;

    public Double getTodayTotalAmount() {
        return todayTotalAmount;
    }

    public void setTodayTotalAmount(Double todayTotalAmount) {
        this.todayTotalAmount = todayTotalAmount;
    }

    public Integer getTodayTotalNum() {
        return todayTotalNum;
    }

    public void setTodayTotalNum(Integer todayTotalNum) {
        this.todayTotalNum = todayTotalNum;
    }

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