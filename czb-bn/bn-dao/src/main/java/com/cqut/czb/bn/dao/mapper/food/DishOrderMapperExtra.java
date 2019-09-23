package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DetailsOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.OrderDishDTO;

import java.util.List;

public interface DishOrderMapperExtra {
    List<DishOrderDTO> selectOrderByUser(String id);

    DetailsOrderDTO selectOrderDetails(DishOrderDTO dishOrderDTO);
}
