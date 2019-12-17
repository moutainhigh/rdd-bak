package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DetailsOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.OrderDishDTO;
import com.cqut.czb.bn.entity.entity.Shop;

import java.util.List;

public interface DishOrderMapperExtra {
    List<DishOrderDTO> selectOrderByUser(String id);

    DishOrderDTO selectByOrderId(String orderId);

    DetailsOrderDTO selectOrderDetails(DishOrderDTO dishOrderDTO);

    int updateDishOrderById(DishOrderDTO dishOrderDTO);

    Shop selectShopByUser(String userId);

    int updateDishOrderByShop(DishOrderDTO dishOrderDTO);
}
