package com.cqut.czb.bn.entity.entity;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
    private String roleId;

    private String roleName;

    private Boolean isAllowEdit;

    private Boolean isAllowDelete;

    private String createId;

    private Date createTime;

    private String updateId;

    private Date updateTime;

    private Boolean isDelete;

    private String dataScope;

    private String roleType;

    public Role(String roleId, String roleName, Boolean isAllowEdit, Boolean isAllowDelete, String createId, Date createTime, String updateId, Date updateTime, Boolean isDelete, String dataScope, String roleType) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isAllowEdit = isAllowEdit;
        this.isAllowDelete = isAllowDelete;
        this.createId = createId;
        this.createTime = createTime;
        this.updateId = updateId;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.dataScope = dataScope;
        this.roleType = roleType;
    }

    public Role() {
        super();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Boolean getIsAllowEdit() {
        return isAllowEdit;
    }

    public void setIsAllowEdit(Boolean isAllowEdit) {
        this.isAllowEdit = isAllowEdit;
    }

    public Boolean getIsAllowDelete() {
        return isAllowDelete;
    }

    public void setIsAllowDelete(Boolean isAllowDelete) {
        this.isAllowDelete = isAllowDelete;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope == null ? null : dataScope.trim();
    }

    public Boolean getAllowEdit() {
        return isAllowEdit;
    }

    public void setAllowEdit(Boolean allowEdit) {
        isAllowEdit = allowEdit;
    }

    public Boolean getAllowDelete() {
        return isAllowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        isAllowDelete = allowDelete;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}