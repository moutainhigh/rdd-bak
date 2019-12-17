package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.entity.food.Dish;

public interface DishMapper {
    int deleteByPrimaryKey(String dishId);

    int insert(Dish record);

    int insertSelective(Dish record);

    Dish selectByPrimaryKey(String dishId);

    int updateByPrimaryKeySelective(Dish record);

    int updateByPrimaryKey(Dish record);
}