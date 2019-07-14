package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.entity.Role;

import java.util.List;

public interface RoleMapperExtra {

    int insertRole(RoleInputDTO roleInputDTO);

    int deleteRole(RoleInputDTO roleInputDTO);

    int updateRole(RoleInputDTO roleInputDTO);

    List<RoleDTO> selectRole(RoleInputDTO roleInputDTO);

    int findRole(RoleInputDTO roleInputDTO);

    String selectRoleId(String userId);

    List<Role> selectAllRole();
}
