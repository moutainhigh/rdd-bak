package com.cqut.czb.bn.entity.dto.role;

public class ResultRoleDTO {
    private String roleId;

    private String roleName;

    private Boolean isAllowEdit;

    private Boolean isAllowDelete;

    private Boolean actualAllowEdit;

    private String createId;

    private String createTime;

    private String updateId;

    private String updateTime;

    private Boolean isDelete;

    private String updatePerson;

    private String createPerson;

    private String roleType;

    public ResultRoleDTO(){}

    public ResultRoleDTO(String roleId, String roleName, Boolean isAllowEdit, Boolean isAllowDelete, Boolean actualAllowEdit, String createId, String createTime, String updateId, String updateTime, Boolean isDelete, String updatePerson, String createPerson, String roleType) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isAllowEdit = isAllowEdit;
        this.isAllowDelete = isAllowDelete;
        this.actualAllowEdit = actualAllowEdit;
        this.createId = createId;
        this.createTime = createTime;
        this.updateId = updateId;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.updatePerson = updatePerson;
        this.createPerson = createPerson;
        this.roleType = roleType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Boolean getActualAllowEdit() {
        return actualAllowEdit;
    }

    public void setActualAllowEdit(Boolean actualAllowEdit) {
        this.actualAllowEdit = actualAllowEdit;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}