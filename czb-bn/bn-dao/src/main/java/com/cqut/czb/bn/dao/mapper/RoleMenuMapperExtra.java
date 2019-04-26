package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.menu.MenuInputDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.dto.roleMenu.RoleMenuDTO;

import java.util.List;

public interface RoleMenuMapperExtra {

    int insertRoleMenus(List<RoleMenuDTO> roleMenuDTOList);

    int deleteRoleMenus(List<RoleMenuDTO> roleMenuDTOList);

    int insertRoleMenu(RoleMenuDTO roleMenuDTO);

    int deleteRoleMenu(RoleMenuDTO roleMenuDTO);

    List<RoleMenuDTO> selectRoleMenuList(RoleInputDTO roleInputDTO);

    List<RoleMenuDTO> selectRoleMenuList2(MenuInputDTO menuInputDTO);
}