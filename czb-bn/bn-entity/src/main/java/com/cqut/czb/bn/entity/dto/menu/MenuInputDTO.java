package com.cqut.czb.bn.entity.dto.menu;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MenuInputDTO 菜单输入DTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class MenuInputDTO {

    private String roleId;

    private String menuId;

    private List<String> menuIds;

    @NotNull(message = "菜单名不能为空")
    private String menuName;

    private String menuPath;

    private Integer menuLevel;

    private String parent;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
