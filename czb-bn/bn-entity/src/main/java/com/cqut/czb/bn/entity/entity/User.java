package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class User {
    private String userId;

    private String userAccount;

    private String userPsw;

    private Integer userType;

    private Integer userRank;

    private Integer isIdentified;

    private String superiorUser;

    private String superiorUserName;

    private Integer isLoginPc;

    private String userName;

    private String userIdCard;

    private Date createAt;

    private Date updateAt;

    private String userYunId;

    private Integer isDeleted;

    private String commissionLevelOne;

    private String commissionLevelTwo;

    private Date missionStartTime;

    private Date missionEndTime;

    private Integer partner;

    private String oldSuperior;

    private String firstLevelPartner;

    private String secondLevelPartner;

    private Integer isVip;

    private String avatarUrl;

    private String bindingid;

    private Integer isSpecial;

    private String mallPartner;

    private String openId;

    private String bindingPhone;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUserYunId() {
        return userYunId;
    }

    public void setUserYunId(String userYunId) {
        this.userYunId = userYunId == null ? null : userYunId.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCommissionLevelOne() {
        return commissionLevelOne;
    }

    public void setCommissionLevelOne(String commissionLevelOne) {
        this.commissionLevelOne = commissionLevelOne == null ? null : commissionLevelOne.trim();
    }

    public String getCommissionLevelTwo() {
        return commissionLevelTwo;
    }

    public void setCommissionLevelTwo(String commissionLevelTwo) {
        this.commissionLevelTwo = commissionLevelTwo == null ? null : commissionLevelTwo.trim();
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

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public String getOldSuperior() {
        return oldSuperior;
    }

    public void setOldSuperior(String oldSuperior) {
        this.oldSuperior = oldSuperior == null ? null : oldSuperior.trim();
    }

    public String getFirstLevelPartner() {
        return firstLevelPartner;
    }

    public void setFirstLevelPartner(String firstLevelPartner) {
        this.firstLevelPartner = firstLevelPartner == null ? null : firstLevelPartner.trim();
    }

    public String getSecondLevelPartner() {
        return secondLevelPartner;
    }

    public void setSecondLevelPartner(String secondLevelPartner) {
        this.secondLevelPartner = secondLevelPartner == null ? null : secondLevelPartner.trim();
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getBindingid() {
        return bindingid;
    }

    public void setBindingid(String bindingid) {
        this.bindingid = bindingid == null ? null : bindingid.trim();
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getMallPartner() {
        return mallPartner;
    }

    public void setMallPartner(String mallPartner) {
        this.mallPartner = mallPartner == null ? null : mallPartner.trim();
    }

    public String getSuperiorUserName() {
        return superiorUserName;
    }

    public void setSuperiorUserName(String superiorUserName) {
        this.superiorUserName = superiorUserName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBindingPhone() {
        return bindingPhone;
    }

    public void setBindingPhone(String bindingPhone) {
        this.bindingPhone = bindingPhone;
    }
}
