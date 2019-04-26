package com.cqut.czb.bn.entity.dto.role;

import javax.validation.constraints.NotNull;

public class RoleIdDTO {

    @NotNull(message = "角色id不能为空")
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
