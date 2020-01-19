package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AgentCenterBusinessPartnerDto {

    /**
     * 事业合伙人ID
     */
    private String userId;

    /**
     * 事业合伙人电话
     */
    private String userAccount;

    /**
     * 事业合伙人姓名
     */
    private String userName;

    /**
     * 区域
     */
    private String area;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss",timezone = "GMT+8")
    private Date createAt;

    /**
     * 推荐普通合伙人人数
     */
    private Integer commonPartner;

    /**
     * 推荐普通用户人数
     */
    private Integer commonUser;

    /**
     * 是否Vip
     */
    private Integer isVip;

    /**
     * 推荐人账号
     * @return
     */
    private String superiorUser;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getCommonPartner() {
        return commonPartner;
    }

    public void setCommonPartner(Integer commonPartner) {
        this.commonPartner = commonPartner;
    }

    public Integer getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(Integer commonUser) {
        this.commonUser = commonUser;
    }


    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getSuperiorUser() {
        return superiorUser;
    }

    public void setSuperiorUser(String superiorUser) {
        this.superiorUser = superiorUser;
    }
}
