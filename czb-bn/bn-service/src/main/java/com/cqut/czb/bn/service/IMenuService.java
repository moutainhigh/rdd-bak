package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.menu.MenuIdDTO;
import com.cqut.czb.bn.entity.dto.menu.MenuInputDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.Menu;
import com.github.pagehelper.PageInfo;

/**
 * 创建人：曹渝
 * 创建时间：2019/4/21
 * 作用：菜单管理
 */
public interface IMenuService {

    boolean insertMenu(MenuInputDTO menuInputDTO);

    boolean deleteMenu(MenuIdDTO menuIdDTO);

    boolean updateMenu(MenuInputDTO menuInputDTO);

    PageInfo<Menu> selectMenu(MenuInputDTO menuInputDTO, PageDTO pageDTO);
}
