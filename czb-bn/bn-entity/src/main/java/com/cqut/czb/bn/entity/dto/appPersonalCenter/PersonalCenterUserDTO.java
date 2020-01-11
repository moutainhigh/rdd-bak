package com.cqut.czb.bn.entity.dto.appPersonalCenter;

import java.util.List;

/**
 * 创建人：陈德强
 * 作用：个人中心——返回用户表和企业表的信息
 */
public class PersonalCenterUserDTO {

    private String enterpriseName;

    private String contactInfo;

    private String userId;

    private String userAccount;

    private Integer userType;

    private Integer userRank;

    private String userName;

    private String userPsw;

    private Integer isVip;

    private Integer haveVip;

    private String bindingAccount;



    private String roleName;  //新增，用户的角色

    private List<UserRoleDTO> roleNameList;

    private String bindingid;


    public List<UserRoleDTO> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<UserRoleDTO> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public Integer getHaveVip() {
        return haveVip;
    }

    public void setHaveVip(Integer haveVip) {
        this.haveVip = haveVip;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBindingid() {
        return bindingid;
    }

    public void setBindingid(String bindingid) {
        this.bindingid = bindingid;
    }

    public String getBindingAccount() {
        return bindingAccount;
    }

    public void setBindingAccount(String bindingAccount) {
        this.bindingAccount = bindingAccount;
    }
}
