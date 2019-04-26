package com.cqut.czb.bn.entity.dto.role;

import javax.validation.constraints.NotNull;

/**
 * RoleInputDTO 角色输入DTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class RoleInputDTO {

    private String roleId;

    @NotNull(message = "角色名不能为空")
    private String roleName;

    private String authorities;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
