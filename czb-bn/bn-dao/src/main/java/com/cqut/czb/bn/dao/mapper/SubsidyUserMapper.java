package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.SubsidyUser;

public interface SubsidyUserMapper {
    int deleteByPrimaryKey(String relationId);

    int insert(SubsidyUser record);

    int insertSelective(SubsidyUser record);

    SubsidyUser selectByPrimaryKey(String relationId);

    int updateByPrimaryKeySelective(SubsidyUser record);

    int updateByPrimaryKey(SubsidyUser record);
}