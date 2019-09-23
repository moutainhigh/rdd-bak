package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.food.Dish;

import java.util.List;

public interface DishMapperExtra {
    List<Dish> selectRecommendDish();

    List<DishShopDTO> selectDishShop(DishShopDTO shop);
}
