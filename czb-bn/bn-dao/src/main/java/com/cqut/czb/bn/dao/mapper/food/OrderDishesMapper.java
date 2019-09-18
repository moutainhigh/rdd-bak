package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.entity.food.OrderDishes;

public interface OrderDishesMapper {
    int deleteByPrimaryKey(String orderDishesId);

    int insert(OrderDishes record);

    int insertSelective(OrderDishes record);

    OrderDishes selectByPrimaryKey(String orderDishesId);

    int updateByPrimaryKeySelective(OrderDishes record);

    int updateByPrimaryKey(OrderDishes record);
}