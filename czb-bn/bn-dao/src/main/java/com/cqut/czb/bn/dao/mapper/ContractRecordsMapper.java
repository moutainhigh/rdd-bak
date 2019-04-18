package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ContractRecords;

public interface ContractRecordsMapper {
    int insert(ContractRecords record);

    int insertSelective(ContractRecords record);
}