package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AgentCenterCommonPartnerDto {

    /**
     * 普通合伙人ID
     */
    private String userId;

    /**
     * 普通合伙人电话
     */
    private String userAccount;

    /**
     * 普通合伙人姓名
     */
    private String userName;

    /**
     * 区域
     */
    private String area;


    /**
     * 推荐人电话
     */
    private String spreadAccount;

    /**
     * 推荐人姓名
     */
    private String spreadName;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss",timezone = "GMT+8")
    private Date createAt;

    /**
     * 推荐普通用户人数
     */
    private Integer commonUser;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArea() {
        return area;
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

    public String getSpreadName() {
        return spreadName;
    }

    public void setSpreadName(String spreadName) {
        this.spreadName = spreadName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(Integer commonUser) {
        this.commonUser = commonUser;
    }
}
