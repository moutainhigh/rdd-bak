package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.OrderDishDTO;

import java.util.List;

public interface OrderDishesMapperExtra {
    List<OrderDishDTO> selectDishInOrder(String orderId);
}
