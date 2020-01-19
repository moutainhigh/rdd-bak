package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import java.util.Date;

public class BusinessCommonUserVo {
    private String userId;
    private String mobile;
    private  Date createAt;
    private String area;
    private String promotionMobile;
    private String spreadAccount;
    private Integer isVip;

    public BusinessCommonUserVo(String mobile, Date createAt, String area) {
        this.mobile = mobile;
        this.createAt = createAt;
        this.area = area;
    }

    public BusinessCommonUserVo(String mobile, Date createAt, String area, String spreadAccount, Integer isVip){
        this.mobile = mobile;
        this.createAt = createAt;
        this.area = area;
        this.spreadAccount = spreadAccount;
        this.isVip = isVip;
    }


    public BusinessCommonUserVo(String userId, String mobile, Date createAt, String area,String promotionMobile,Integer isVip) {
        this.userId = userId;
        this.mobile = mobile;
        this.createAt = createAt;
        this.area = area;
        this.promotionMobile = promotionMobile;
        this.isVip = isVip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(Integer areaId) {
        this.area = area;
    }

    public String getPromotionMobile() {
        return promotionMobile;
    }

    public void setPromotionMobile(String promotionMobile) {
        this.promotionMobile = promotionMobile;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSpreadAccount() {
        return spreadAccount;
    }

    public void setSpreadAccount(String spreadAccount) {
        this.spreadAccount = spreadAccount;
    }
}
