package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class VipAreaConfig {
    private String vipAreaConfigId;

    private String area;

    private Integer vipState;

    private Double vipPrice;

    private Date createAt;

    private Date updateAt;

    private Double fyRate;

    private Double fyOne;

    private Double fyTwo;

    private Integer isSpecial;

    public String getVipAreaConfigId() {
        return vipAreaConfigId;
    }

    public void setVipAreaConfigId(String vipAreaConfigId) {
        this.vipAreaConfigId = vipAreaConfigId == null ? null : vipAreaConfigId.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getVipState() {
        return vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Double getFyRate() {
        return fyRate;
    }

    public void setFyRate(Double fyRate) {
        this.fyRate = fyRate;
    }

    public Double getFyOne() {
        return fyOne;
    }

    public void setFyOne(Double fyOne) {
        this.fyOne = fyOne;
    }

    public Double getFyTwo() {
        return fyTwo;
    }

    public void setFyTwo(Double fyTwo) {
        this.fyTwo = fyTwo;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }
}