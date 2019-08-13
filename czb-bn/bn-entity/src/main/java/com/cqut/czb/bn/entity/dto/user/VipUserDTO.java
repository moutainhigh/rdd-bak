package com.cqut.czb.bn.entity.dto.user;

import com.cqut.czb.bn.entity.entity.User;

public class VipUserDTO {

    private String userId;

    private String superiorUser;

    private User user;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuperiorUser() {
        return superiorUser;
    }

    public void setSuperiorUser(String superiorUser) {
        this.superiorUser = superiorUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
