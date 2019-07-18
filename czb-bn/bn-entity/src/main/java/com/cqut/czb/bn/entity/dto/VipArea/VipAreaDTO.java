package com.cqut.czb.bn.entity.dto.VipArea;

import com.cqut.czb.bn.entity.dto.PageDTO;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 19:17
 */
public class VipAreaDTO extends PageDTO {
    private String vipAreaConfigId;

    private String area;

    private Integer vipNum;

    private Integer vipState;

    private Double vipPrice;

    public String getVipAreaConfigId() {
        return vipAreaConfigId;
    }

    public void setVipAreaConfigId(String vipAreaConfigId) {
        this.vipAreaConfigId = vipAreaConfigId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getVipNum() {
        return vipNum;
    }

    public void setVipNum(Integer vipNum) {
        this.vipNum = vipNum;
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


}