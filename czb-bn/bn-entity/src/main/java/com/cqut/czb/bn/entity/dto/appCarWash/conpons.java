package com.cqut.czb.bn.entity.dto.appCarWash;

import java.util.Date;

public class conpons {

    //优惠券ID
    private String couponId;

    //优惠劵名称
    private String couponName;

    //截止日期
    private Date destroyTime;

    //优惠券id
    private String couponStandardId;

    //优惠劵类型
    private String standardType;

    //优惠券面额
    private Double standardValue;

    //说明
    private String standardExplain;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Date getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Date destroyTime) {
        this.destroyTime = destroyTime;
    }

    public String getCouponStandardId() {
        return couponStandardId;
    }

    public void setCouponStandardId(String couponStandardId) {
        this.couponStandardId = couponStandardId;
    }

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public String getStandardExplain() {
        return standardExplain;
    }

    public void setStandardExplain(String standardExplain) {
        this.standardExplain = standardExplain;
    }
}
