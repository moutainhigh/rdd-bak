package com.cqut.czb.bn.entity.entity.partnerAndOperateCenter;

import java.util.Date;

public class GetGeneralPartnerListVo {

    //登录者id
    private String userId;

    //选择搜索电话
    private String account;

    //选择搜索注册时间
    private Date creatAt;

    //选择搜索区域id
    private Integer areaId;

    public GetGeneralPartnerListVo(String userId, String account, Date creatAt, Integer areaId) {
        this.userId = userId;
        this.account = account;
        this.areaId = areaId;
        this.creatAt = creatAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Date getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(Date creatAt) {
        this.creatAt = creatAt;
    }
}
