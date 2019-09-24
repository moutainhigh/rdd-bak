package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.food.Dish;

import java.util.List;

public interface AppDishService {
    List<DishDTO> getRecommendDishList();

    List<DishShopDTO> getAllDishShop(DishShopDTO shop);
}
