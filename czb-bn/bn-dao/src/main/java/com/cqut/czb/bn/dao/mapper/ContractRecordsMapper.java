package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ContractRecords;

public interface ContractRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(ContractRecords record);

    int insertSelective(ContractRecords record);

    ContractRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(ContractRecords record);

    int updateByPrimaryKey(ContractRecords record);
}