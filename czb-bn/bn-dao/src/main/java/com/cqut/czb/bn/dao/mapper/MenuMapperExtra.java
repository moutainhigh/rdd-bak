package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.menu.MenuInputDTO;
import com.cqut.czb.bn.entity.entity.Menu;

import java.util.List;

public interface MenuMapperExtra {

    int insertMenu(MenuInputDTO menuInputDTO);

    int deleteMenu(MenuInputDTO menuInputDTO);

    int updateMenu(MenuInputDTO menuInputDTO);

    List<Menu> selectMenu(MenuInputDTO menuInputDTO);

    List<Menu> selectRolesMenu(MenuInputDTO menuInputDTO);

    List<Menu> selectParentMenu();
}
