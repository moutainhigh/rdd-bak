package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.UserIncomeInfo;

public interface UserIncomeInfoMapper {
    int deleteByPrimaryKey(String infoId);

    int insert(UserIncomeInfo record);

    int insertSelective(UserIncomeInfo record);

    UserIncomeInfo selectByPrimaryKey(String infoId);

    int updateByPrimaryKeySelective(UserIncomeInfo record);

    int updateByPrimaryKey(UserIncomeInfo record);
}