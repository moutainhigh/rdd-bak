package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.LoginInfo;

public interface LoginInfoMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(LoginInfo record);

    int insertSelective(LoginInfo record);

    LoginInfo selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(LoginInfo record);

    int updateByPrimaryKey(LoginInfo record);
}