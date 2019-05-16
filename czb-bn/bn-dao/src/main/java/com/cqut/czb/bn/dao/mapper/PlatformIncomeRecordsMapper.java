package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;

public interface PlatformIncomeRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PlatformIncomeRecords record);

    int insertSelective(PlatformIncomeRecords record);

    PlatformIncomeRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PlatformIncomeRecords record);

    int updateByPrimaryKey(PlatformIncomeRecords record);
}