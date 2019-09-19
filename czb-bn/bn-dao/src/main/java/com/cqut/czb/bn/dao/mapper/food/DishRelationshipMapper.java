package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.entity.food.DishRelationship;

public interface DishRelationshipMapper {
    int deleteByPrimaryKey(String dishRelationshipId);

    int insert(DishRelationship record);

    int insertSelective(DishRelationship record);

    DishRelationship selectByPrimaryKey(String dishRelationshipId);

    int updateByPrimaryKeySelective(DishRelationship record);

    int updateByPrimaryKey(DishRelationship record);
}