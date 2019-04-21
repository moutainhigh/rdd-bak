package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.entity.Role;
import com.github.pagehelper.PageInfo;

public interface IRoleService {

    boolean insertRole(RoleInputDTO roleInputDTO);

    boolean deleteRole(RoleInputDTO roleInputDTO);

    boolean updateRole(RoleInputDTO roleInputDTO);

    PageInfo<Role> selectRole(RoleInputDTO roleInputDTO, PageDTO pageDTO);
}
