package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.MenuMapperExtra;
import com.cqut.czb.bn.dao.mapper.RoleMenuMapperExtra;
import com.cqut.czb.bn.entity.dto.menu.MenuIdDTO;
import com.cqut.czb.bn.entity.dto.menu.MenuInputDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.roleMenu.RoleMenuDTO;
import com.cqut.czb.bn.entity.entity.Menu;
import com.cqut.czb.bn.service.IMenuService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Autowired
    MenuMapperExtra menuMapperExtra;

    @Autowired
    RoleMenuMapperExtra roleMenuMapperExtra;

    @Override
    public boolean insertMenu(MenuInputDTO menuInputDTO) {
        menuInputDTO.setMenuId(StringUtil.createId());
        return menuMapperExtra.insertMenu(menuInputDTO) > 0;
    }

    @Override
    public boolean deleteMenu(MenuIdDTO menuIdDTO) {
        MenuInputDTO menuInputDTO = new MenuInputDTO();
        menuInputDTO.setMenuId(menuIdDTO.getMenuId());
        List<RoleMenuDTO> roleMenuDTOList = roleMenuMapperExtra.selectRoleMenuList2(menuInputDTO);
        boolean isDeleteRoleMenu = true;
        if(roleMenuDTOList.size() > 0) {
            isDeleteRoleMenu = roleMenuMapperExtra.deleteRoleMenus(roleMenuDTOList) > 0;
        }
        if(isDeleteRoleMenu) {
            return menuMapperExtra.deleteMenu(menuInputDTO) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateMenu(MenuInputDTO menuInputDTO) {
        return menuMapperExtra.updateMenu(menuInputDTO) > 0;
    }

    @Override
    public PageInfo<Menu> selectMenu(MenuInputDTO menuInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Menu> menuList = menuMapperExtra.selectMenu(menuInputDTO);
        return new PageInfo<>(menuList);
    }

    @Override
    public List<Menu> selectParentMenu() {
        return menuMapperExtra.selectParentMenu();
    }
}
