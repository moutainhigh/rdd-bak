package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleIdDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.github.pagehelper.PageInfo;

/**
 * 创建人：曹渝
 * 创建时间：2019/4/21
 * 作用：角色管理
 */
public interface IRoleService {

    boolean insertRole(RoleInputDTO roleInputDTO);

    boolean deleteRole(RoleIdDTO roleIdDTO);

    boolean updateRole(RoleInputDTO roleInputDTO);

    PageInfo<RoleDTO> selectRole(RoleInputDTO roleInputDTO, PageDTO pageDTO);

    String selectRoleId(String userId);
}
