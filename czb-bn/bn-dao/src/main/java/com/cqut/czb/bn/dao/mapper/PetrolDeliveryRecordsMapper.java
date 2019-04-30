package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PetrolDeliveryRecords;

public interface PetrolDeliveryRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PetrolDeliveryRecords record);

    int insertSelective(PetrolDeliveryRecords record);

    PetrolDeliveryRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PetrolDeliveryRecords record);

    int updateByPrimaryKey(PetrolDeliveryRecords record);
}