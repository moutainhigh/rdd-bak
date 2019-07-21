package com.cqut.czb.bn.entity.entity.subsidyManage;

import com.github.pagehelper.PageInfo;

public class SeeSubsidyListDTO {
    // 分页后的数据
    private PageInfo<SeeSubsidy>  seeSubsidyPageInfo;

    // 总人数
    private Integer peopleAmount;

    // 总补贴金额
    private Double amountAll;

    public PageInfo<SeeSubsidy> getSeeSubsidyPageInfo() {
        return seeSubsidyPageInfo;
    }

    public void setSeeSubsidyPageInfo(PageInfo<SeeSubsidy> seeSubsidyPageInfo) {
        this.seeSubsidyPageInfo = seeSubsidyPageInfo;
    }

    public Integer getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(Integer peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public Double getAmountAll() {
        return amountAll;
    }

    public void setAmountAll(Double amountAll) {
        this.amountAll = amountAll;
    }
}
