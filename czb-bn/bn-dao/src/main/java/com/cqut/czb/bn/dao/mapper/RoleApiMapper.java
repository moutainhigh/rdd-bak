package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.RoleApi;

public interface RoleApiMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoleApi record);

    int insertSelective(RoleApi record);

    RoleApi selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleApi record);

    int updateByPrimaryKey(RoleApi record);
}