package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;

public interface PetrolSalesRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PetrolSalesRecords record);

    int insertSelective(PetrolSalesRecords record);

    PetrolSalesRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PetrolSalesRecords record);

    int updateByPrimaryKey(PetrolSalesRecords record);
}