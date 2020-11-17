package com.cqut.czb.bn.dao.mapper.withoutCard;

import com.cqut.czb.bn.entity.entity.withoutCard.PetrolSalesRecordsWithoutCard;

public interface PetrolSalesRecordsWithoutCardMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PetrolSalesRecordsWithoutCard record);

    int insertSelective(PetrolSalesRecordsWithoutCard record);

    PetrolSalesRecordsWithoutCard selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PetrolSalesRecordsWithoutCard record);

    int updateByPrimaryKey(PetrolSalesRecordsWithoutCard record);
}