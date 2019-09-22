package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.entity.food.DishOrder;

public interface DishOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(DishOrder record);

    int insertSelective(DishOrder record);

    DishOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(DishOrder record);

    int updateByPrimaryKey(DishOrder record);
}