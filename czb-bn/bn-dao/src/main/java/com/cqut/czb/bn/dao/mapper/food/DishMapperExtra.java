package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.food.AppOrderPage.AllDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderNum;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderToRecommendDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.InputRecommendDishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.SearchDishShopDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.SearchInputDTO;
import org.apache.ibatis.annotations.Param;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.food.Dish;

import java.util.List;

public interface DishMapperExtra {
    List<SearchDishShopDTO> selectRecommendDishShop(InputRecommendDishDTO inputRecommendDishDTO);

    List<Shop> selectDishShop(Shop shop);

    List<DishDTO> findTodayFood();

    List<SearchDishShopDTO> selectDishShopByName(SearchInputDTO searchInputDTO);

    List<DishDTO> selectDishByName(SearchInputDTO searchInputDTO);

    List<OrderToRecommendDTO>  selectRecommend(@Param("shopId") String shopId,@Param("weekDay") String weekDay);

    List<AllDishDTO> selectAllDish(@Param("shopId") String shopId,@Param("weekDay") String weekDay);

    List<DishShopDTO> selectDishShop(DishShopDTO shop);

    //多个id查询商品信息
    List<DishDTO> selectOrderDish(List<OrderNum> list);

    //查询商品信息
    List<OrderToRecommendDTO> selectDishs(List<OrderNum> list);
}