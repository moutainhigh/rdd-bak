package com.cqut.czb.bn.entity.dto.role;

import com.cqut.czb.bn.entity.entity.Menu;

import java.util.List;

/**
 * RoleDTO 角色DTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class RoleDTO {

    private String roleId;

    private String roleName;

    private List<Menu> menuList;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

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
}
