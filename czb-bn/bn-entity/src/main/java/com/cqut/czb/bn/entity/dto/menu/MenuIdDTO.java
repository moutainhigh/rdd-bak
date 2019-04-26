package com.cqut.czb.bn.entity.dto.menu;

import javax.validation.constraints.NotNull;

/**
 * MenuIdDTO 菜单idDTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class MenuIdDTO {

    @NotNull(message = "菜单Id不能为空")
    private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
