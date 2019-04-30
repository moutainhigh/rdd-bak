package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;

public interface PetrolSaleConfigMapper {
    int deleteByPrimaryKey(String petrolConfigId);

    int insert(PetrolSaleConfig record);

    int insertSelective(PetrolSaleConfig record);

    PetrolSaleConfig selectByPrimaryKey(String petrolConfigId);

    int updateByPrimaryKeySelective(PetrolSaleConfig record);

    int updateByPrimaryKey(PetrolSaleConfig record);
}