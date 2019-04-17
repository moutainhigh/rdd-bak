package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(Role record);

    Role selectByPrimaryKey(String roleId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> selectUserRoles(@Param("roleIds") List<String> roleIds);
}