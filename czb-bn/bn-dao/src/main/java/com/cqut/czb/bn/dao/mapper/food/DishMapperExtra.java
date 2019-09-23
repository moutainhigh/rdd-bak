package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.food.AppOrderPage.AllDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderToRecommendDTO;
import org.apache.ibatis.annotations.Param;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.food.Dish;

import java.util.List;

public interface DishMapperExtra {
    List<Dish> selectRecommendDish();

    List<Shop> selectDishShop(Shop shop);

    List<OrderToRecommendDTO>  selectRecommend(@Param("shopId") String shopId);

    List<AllDishDTO> selectAllDish(@Param("shopId") String shopId);

}