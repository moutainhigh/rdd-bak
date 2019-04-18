package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.DepositRecords;

public interface DepositRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(DepositRecords record);

    int insertSelective(DepositRecords record);

    DepositRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(DepositRecords record);

    int updateByPrimaryKey(DepositRecords record);
}