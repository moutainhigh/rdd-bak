package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String selectBindingAccount(String bindingId);
}