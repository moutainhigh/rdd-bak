package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.ManageFood.SetInfo;
import com.cqut.czb.bn.entity.dto.ManageFood.ShopInfo;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.food.Dish;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishSystemMapperExtra {
    int insert(Food food);

    int delete(@Param("dishId") String foodId);

    int edit(Food food);

    Page<Food> search(Food food);

    String getShopId(@Param("userId") String userId);

    List<SetInfo> getSetInfo(@Param("setMealId") String setMealId);

    int insertSets(@Param("list") List<SetInfo> list);

    int deleteList(@Param("list") List<SetInfo> list);

    // 获得商店
    Page<ShopInfo> getShops(ShopInfo shopInfo);
}
