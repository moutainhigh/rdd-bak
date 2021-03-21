package com.cqut.czb.bn.entity.dto.user;

public class PersonalUserDTO {

    private String userAccount;

    private String userName;

    private String userIdCard;

    private String userPsw;

    private String content;

    private String superiorUser;

    private String avatarUrl;

    private String mallPartner;

    private String mallPartnerAccount;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
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

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getSuperiorUser() {
        return superiorUser;
    }

    public void setSuperiorUser(String superiorUser) {
        this.superiorUser = superiorUser;
    }

    public String getMallPartner() {
        return mallPartner;
    }

    public void setMallPartner(String mallPartner) {
        this.mallPartner = mallPartner;
    }

    public String getMallPartnerAccount() {
        return mallPartnerAccount;
    }

    public void setMallPartnerAccount(String mallPartnerAccount) {
        this.mallPartnerAccount = mallPartnerAccount;
    }
}
