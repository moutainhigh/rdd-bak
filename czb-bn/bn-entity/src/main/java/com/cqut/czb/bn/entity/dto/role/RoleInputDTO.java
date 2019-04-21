package com.cqut.czb.bn.entity.dto.role;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * RoleInputDTO 角色信息DTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/5
 */
public class RoleInputDTO {

    private String roleId;

    private String roleName;

    private List<String> authorities;

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

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
