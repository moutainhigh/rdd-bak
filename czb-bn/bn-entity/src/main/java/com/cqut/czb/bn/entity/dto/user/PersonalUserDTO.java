package com.cqut.czb.bn.entity.dto.user;

public class PersonalUserDTO {

    private String userAccount;

    private String userName;

    private String userIdCard;

    private String userPsw;

    private String content;

    private String superiorUser;

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
}
