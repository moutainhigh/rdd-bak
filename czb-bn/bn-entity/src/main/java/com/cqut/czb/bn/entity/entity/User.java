package com.cqut.czb.bn.entity.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {
    private String userId;

    private String userName;

    @NotNull(message = "账号不能为空")
    private String account;

    private String password;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}