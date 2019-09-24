package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.InputRecommendDishDTO;

import java.util.List;

public interface AppDishService {
    List<DishDTO> getRecommendDishList(InputRecommendDishDTO inputRecommendDishDTO);

    List<DishShopDTO> getAllDishShop(DishShopDTO shop);
}
