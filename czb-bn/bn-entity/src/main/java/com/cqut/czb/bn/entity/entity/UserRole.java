package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class UserRole {
    private String id;

    private String roleId;

    private String userId;

    private Date ctreateAt;

    private Date updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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