package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import java.util.Date;

public class BusinessCommonUserVo {
    private String userId;
    private String mobile;
    private  Date createAt;
    private Integer areaId;

    public BusinessCommonUserVo(String userId, String mobile, Date createAt, Integer areaId) {
        this.userId = userId;
        this.mobile = mobile;
        this.createAt = createAt;
        this.areaId = areaId;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
