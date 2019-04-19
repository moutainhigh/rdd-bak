package com.cqut.czb.bn.entity.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {
    private String userId;

    @NotNull(message = "账号不能为空")
    private String userAccount;

    private String userPsw;

    private Integer userType;

    private Integer userRank;

    private Integer isIdentified;

    private String superiorUser;

    private Integer isLoginPc;

    private String userName;

    private String userIdCard;

    private Date ctreateAt;

    private Date updateAt;

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

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw == null ? null : userPsw.trim();
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

    public Integer getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(Integer isIdentified) {
        this.isIdentified = isIdentified;
    }

    public String getSuperiorUser() {
        return superiorUser;
    }

    public void setSuperiorUser(String superiorUser) {
        this.superiorUser = superiorUser == null ? null : superiorUser.trim();
    }

    public Integer getIsLoginPc() {
        return isLoginPc;
    }

    public void setIsLoginPc(Integer isLoginPc) {
        this.isLoginPc = isLoginPc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard == null ? null : userIdCard.trim();
    }

    public Date getCtreateAt() {
        return ctreateAt;
    }

    public void setCtreateAt(Date ctreateAt) {
        this.ctreateAt = ctreateAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}