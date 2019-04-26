package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.RoleMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserRoleMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.Role;
import com.cqut.czb.bn.entity.entity.UserRole;
import com.cqut.czb.bn.service.IUserService;
import com.cqut.czb.bn.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;

    @Autowired
    RoleMapperExtra roleMapperExtra;

    @Override
    public boolean deleteUser(UserIdDTO userIdDTO) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userIdDTO.getUserId());
        List<UserRole> userRoleList = userRoleMapperExtra.slectUserRoleList(userRole);
        boolean isDelete = true;
        if(userRoleList.size() > 0) {
            isDelete = userRoleMapperExtra.deleteUserRoles(userRoleList) > 0;
        }
        if(isDelete) {
            return userMapperExtra.deleteUser(userIdDTO) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateUser(UserInputDTO userInputDTO) {
        return false;
    }

    @Override
    public PageInfo<UserDTO> selectUser(UserInputDTO userInputDTO, PageDTO pageDTO) {
        if(userInputDTO.getCreateAt() != null) {
            userInputDTO.setCreateStartTime(DateUtil.getDateStart(userInputDTO.getCreateAt()));
            userInputDTO.setCreateEndTime(DateUtil.getDateEnd(userInputDTO.getCreateAt()));
        }
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<UserDTO> userList = userMapperExtra.selectUser(userInputDTO);
        for(UserDTO userDTO: userList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userDTO.getUserId());
            List<UserRole> userRoleList = userRoleMapperExtra.slectUserRoleList(userRole);
            for(UserRole userRole1: userRoleList) {
                RoleInputDTO roleInputDTO = new RoleInputDTO();
                roleInputDTO.setRoleId(userRole1.getRoleId());
                List<RoleDTO> roleDTOList = roleMapperExtra.selectRole(roleInputDTO);
                if(roleDTOList.size() > 0) {
                    userDTO.setRoleList(roleDTOList);
                }
            }
        }
        return new PageInfo<>(userList);
    }
}
