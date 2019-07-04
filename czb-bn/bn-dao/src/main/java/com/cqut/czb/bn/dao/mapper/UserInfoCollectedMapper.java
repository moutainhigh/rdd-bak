package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.UserInfoCollected;

public interface UserInfoCollectedMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserInfoCollected record);

    int insertSelective(UserInfoCollected record);

    UserInfoCollected selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserInfoCollected record);

    int updateByPrimaryKey(UserInfoCollected record);
}