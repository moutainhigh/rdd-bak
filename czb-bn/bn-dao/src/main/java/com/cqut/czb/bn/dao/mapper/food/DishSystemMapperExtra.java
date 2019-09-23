package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.food.Dish;
import com.github.pagehelper.Page;

import java.util.List;

public interface DishSystemMapperExtra {
    int insert(Food food);

    int delete(String foodId);

    int edit(Food food);

    Page<Food> search(Food food);
}
