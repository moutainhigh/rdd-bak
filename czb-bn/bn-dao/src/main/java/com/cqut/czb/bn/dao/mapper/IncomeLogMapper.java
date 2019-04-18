package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.IncomeLog;

public interface IncomeLogMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(IncomeLog record);

    int insertSelective(IncomeLog record);

    IncomeLog selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(IncomeLog record);

    int updateByPrimaryKey(IncomeLog record);
}