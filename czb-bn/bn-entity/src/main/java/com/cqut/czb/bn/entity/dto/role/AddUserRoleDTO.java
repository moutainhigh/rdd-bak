package com.cqut.czb.bn.entity.dto.role;

/**
 * userManagementDTO 存入用户角色信息
 * 设计者:   罗杏函
 * 更新日期: 2018-04-09
 */
public class AddUserRoleDTO{

    /**
     * 角色名
     */
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        if (roleId == ""){
            this.roleId = null;
        }else {
            this.roleId = roleId;
        }
    }
}
