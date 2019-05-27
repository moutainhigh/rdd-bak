package com.cqut.czb.bn.entity.dto.appHomePage;

import java.util.List;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/22
 * 作用：用卡专区数据
 */
public class PetrolZoneDTO {

    private String petrolName;

    private Integer petrolKind;

    private String petrolRemark;//油卡备注（字典）

    private List<petrolInfoDTO> petrolPriceInfo;//油卡价格相关信息

    private String area;

    public String getPetrolRemark() {
        return petrolRemark;
    }

    public void setPetrolRemark(String petrolRemark) {
        this.petrolRemark = petrolRemark;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPetrolName() {
        return petrolName;
    }

    public void setPetrolName(String petrolName) {
        this.petrolName = petrolName;
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public List<petrolInfoDTO> getPetrolPriceInfo() {
        return petrolPriceInfo;
    }

    public void setPetrolPriceInfo(List<petrolInfoDTO> petrolPriceInfo) {
        this.petrolPriceInfo = petrolPriceInfo;
    }
}
