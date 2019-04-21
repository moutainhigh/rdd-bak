package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.RoleMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.entity.Role;
import com.cqut.czb.bn.service.IRoleService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapperExtra roleMapperExtra;

    @Override
    public boolean insertRole(RoleInputDTO roleInputDTO) {
        roleInputDTO.setRoleId(StringUtil.createId());
        return roleMapperExtra.insertRole(roleInputDTO) > 0;
    }

    @Override
    public boolean deleteRole(RoleInputDTO roleInputDTO) {
        return roleMapperExtra.deleteRole(roleInputDTO) > 0;
    }

    @Override
    public boolean updateRole(RoleInputDTO roleInputDTO) {
        return roleMapperExtra.updateRole(roleInputDTO) > 0;
    }

    @Override
    public PageInfo<Role> selectRole(RoleInputDTO roleInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<Role> roleList = roleMapperExtra.selectRole(roleInputDTO);
        return new PageInfo<>(roleList);
    }
}
