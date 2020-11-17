package com.cqut.czb.bn.dao.mapper.withoutCard;

import com.cqut.czb.bn.entity.entity.withoutCard.PetrolWithoutCard;

public interface PetrolWithoutCardMapper {
    int deleteByPrimaryKey(String petrolId);

    int insert(PetrolWithoutCard record);

    int insertSelective(PetrolWithoutCard record);

    PetrolWithoutCard selectByPrimaryKey(String petrolId);

    int updateByPrimaryKeySelective(PetrolWithoutCard record);

    int updateByPrimaryKey(PetrolWithoutCard record);
}