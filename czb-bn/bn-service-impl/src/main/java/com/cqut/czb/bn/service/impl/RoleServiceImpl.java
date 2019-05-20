package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.MenuMapperExtra;
import com.cqut.czb.bn.dao.mapper.RoleMapperExtra;
import com.cqut.czb.bn.dao.mapper.RoleMenuMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserRoleMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.menu.MenuInputDTO;
import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleIdDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.dto.roleMenu.RoleMenuDTO;
import com.cqut.czb.bn.entity.entity.UserRole;
import com.cqut.czb.bn.service.IRoleService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapperExtra roleMapperExtra;

    @Autowired
    RoleMenuMapperExtra roleMenuMapperExtra;

    @Autowired
    MenuMapperExtra menuMapperExtra;

    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;

    @Override
    public boolean insertRole(RoleInputDTO roleInputDTO) {
        roleInputDTO.setRoleId(StringUtil.createId());
        boolean isInsert = true;
        if(roleInputDTO.getAuthorities() != null) {
            List<RoleMenuDTO> roleMenuDTOList = initRoleMenuList(roleInputDTO);
            isInsert = roleMenuMapperExtra.insertRoleMenus(roleMenuDTOList) > 0;
        }
        if(isInsert) {
            return roleMapperExtra.insertRole(roleInputDTO) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteRole(RoleIdDTO roleIdDTO) {
        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleId(roleIdDTO.getRoleId());
        List<RoleMenuDTO> roleMenuDTOList = roleMenuMapperExtra.selectRoleMenuList(roleInputDTO);
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleIdDTO.getRoleId());
        List<UserRole> userRoleList = userRoleMapperExtra.slectUserRoleList(userRole);
        boolean isDeleteUserRole = true;
        if(userRoleList.size() >0) {
            isDeleteUserRole = userRoleMapperExtra.deleteUserRoles(userRoleList) > 0;
        }
        boolean isDeleteRoleMenu = true;
        if(roleMenuDTOList.size() > 0) {
            isDeleteRoleMenu = roleMenuMapperExtra.deleteRoleMenus(roleMenuDTOList) > 0;
        }
        if(isDeleteUserRole && isDeleteRoleMenu) {
            return roleMapperExtra.deleteRole(roleInputDTO) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateRole(RoleInputDTO roleInputDTO) {
        List<RoleMenuDTO> tempList = new ArrayList<>();
        List<RoleMenuDTO> deleteList = roleMenuMapperExtra.selectRoleMenuList(roleInputDTO);
        boolean isInsert = true;
        if(roleInputDTO.getAuthorities() != null) {
            List<RoleMenuDTO> insertList = initRoleMenuList(roleInputDTO);
            for (RoleMenuDTO delete : deleteList) {
                for(RoleMenuDTO insert : insertList) {
                    if (delete.getMenuId().equals(insert.getMenuId())) {
                        tempList.add(delete);
                        insertList.remove(insert);
                        break;
                    }
                }
            }
            for(RoleMenuDTO temp: tempList) {
                deleteList.remove(temp);
            }
            if(insertList.size() > 0) {
                isInsert = roleMenuMapperExtra.insertRoleMenus(insertList) > 0;
            }
        }
        boolean isDelete = true;
        if(deleteList.size() > 0) {
            isDelete = roleMenuMapperExtra.deleteRoleMenus(deleteList) > 0;
        }
        if(isInsert && isDelete) {
            return roleMapperExtra.updateRole(roleInputDTO) > 0;
        } else {
            return false;
        }
    }

    @Override
    public PageInfo<RoleDTO> selectRole(RoleInputDTO roleInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<RoleDTO> roleList = roleMapperExtra.selectRole(roleInputDTO);
        for(RoleDTO roleDTO: roleList) {
            RoleInputDTO roleInput = new RoleInputDTO();
            roleInput.setRoleId(roleDTO.getRoleId());
            List<RoleMenuDTO> roleMenuDTOList = roleMenuMapperExtra.selectRoleMenuList(roleInput);
            MenuInputDTO menuInputDTO = new MenuInputDTO();
            menuInputDTO.setMenuIds(new ArrayList<>());
            for(RoleMenuDTO roleMenuDTO: roleMenuDTOList)  {
                menuInputDTO.getMenuIds().add(roleMenuDTO.getMenuId());
            }
            roleDTO.setMenuList(menuMapperExtra.selectRolesMenu(menuInputDTO));
        }
        return new PageInfo<>(roleList);
    }

    public List<RoleMenuDTO> initRoleMenuList(RoleInputDTO roleInputDTO) {
        List<RoleMenuDTO> roleMenuDTOList = new ArrayList<>();
        for(String authority : roleInputDTO.getAuthorities().split(",")) {
            RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
            roleMenuDTO.setId(StringUtil.createId());
            roleMenuDTO.setRoleId(roleInputDTO.getRoleId());
            roleMenuDTO.setMenuId(authority);
            roleMenuDTOList.add(roleMenuDTO);
        }

        return roleMenuDTOList;
    }
}
