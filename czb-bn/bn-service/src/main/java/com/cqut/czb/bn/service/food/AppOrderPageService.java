package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.food.AppOrderPage.AllDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.InputOrderPageDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderPageShopDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderToRecommendDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;

import java.util.List;

/**
 * app 点餐页面
 */
public interface AppOrderPageService {

    OrderPageShopDTO selectOrderShopInfo(InputOrderPageDTO inputOrderPageDTO);

    List<OrderToRecommendDTO> selectRecommend(String shopId);

    List<AllDishDTO> selectAllDish(String shopId);

    List<OrderToRecommendDTO> selectDishInfo(String dishInfo);

}
