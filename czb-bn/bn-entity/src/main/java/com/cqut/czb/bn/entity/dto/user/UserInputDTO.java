package com.cqut.czb.bn.entity.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * UserInputDTO 用户输入DTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class UserInputDTO {

    private String userId;

    private Integer isSpecial;

    private String userAccount;

    @JsonIgnore
    private String password;

    private String userName;

    private Integer partner;

    private String superiorUser;

    private String superiorUserName;

    private Integer userType;

    private Integer userRank;

    private String roleId;
    /**
     * 注册时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    /**
     * 指标开始时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date missionStartTime;

    private Date missionEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer isLoginPc;

    private Integer isVip;

    private String oldSuperior;

    private String firstLevelPartner;

    private String secondLevelPartner;

    private String bindingid;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSuperiorUser() {
        return superiorUser;
    }

    public void setSuperiorUser(String superiorUser) {
        this.superiorUser = superiorUser;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public Date getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(Date missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public Date getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(Date missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsLoginPc() {
        return isLoginPc;
    }

    public void setIsLoginPc(Integer isLoginPc) {
        this.isLoginPc = isLoginPc;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getOldSuperior() {
        return oldSuperior;
    }

    public void setOldSuperior(String oldSuperior) {
        this.oldSuperior = oldSuperior;
    }

    public String getFirstLevelPartner() {
        return firstLevelPartner;
    }

    public void setFirstLevelPartner(String firstLevelPartner) {
        this.firstLevelPartner = firstLevelPartner;
    }

    public String getSecondLevelPartner() {
        return secondLevelPartner;
    }

    public void setSecondLevelPartner(String secondLevelPartner) {
        this.secondLevelPartner = secondLevelPartner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBindingid() {
        return bindingid;
    }

    public void setBindingid(String bindingid) {
        this.bindingid = bindingid;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getSuperiorUserName() {
        return superiorUserName;
    }

    public void setSuperiorUserName(String superiorUserName) {
        this.superiorUserName = superiorUserName;
    }
}
