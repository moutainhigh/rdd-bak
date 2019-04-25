package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.UserRole;

import java.util.List;

public interface UserRoleMapperExtra {

    List<UserRole> slectUserRoleList(UserRole userRole);

    int insertUserRoles(List<UserRole> userRoleList);

    int deleteUserRoles(List<UserRole> userRoleList);
}
