package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class OilCardBinging {

    private String pertrolNum; // 油卡卡号

    private String isPertrolNum; // 确认卡号

    private Integer type; // 油卡类型 0 中石油 1 中石化

    public String getPertrolNum() {
        return pertrolNum;
    }

    public void setPertrolNum(String pertrolNum) {
        this.pertrolNum = pertrolNum;
    }

    public String getIsPertrolNum() {
        return isPertrolNum;
    }

    public void setIsPertrolNum(String isPertrolNum) {
        this.isPertrolNum = isPertrolNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
