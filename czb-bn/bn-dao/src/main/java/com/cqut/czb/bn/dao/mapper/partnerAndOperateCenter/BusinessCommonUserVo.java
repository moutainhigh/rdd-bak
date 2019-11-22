package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import java.util.Date;

public class BusinessCommonUserVo {
    private String userId;
    private String mobile;
    private  Date createAt;
    private String area;

    public BusinessCommonUserVo(String userId, String mobile, Date createAt, String area) {
        this.userId = userId;
        this.mobile = mobile;
        this.createAt = createAt;
        this.area = area;
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
}
