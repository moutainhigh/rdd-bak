package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class VipAreaConfig {
    private String vipAreaConfigId;

    private String area;

    private Integer vipState;

    private Double vipPrice;

    private Date createAt;

    private Date updateAt;

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
}