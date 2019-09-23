package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.food.AppOrderPage.AllDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderPageDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderToRecommendDTO;

import java.util.List;

/**
 * app 点餐页面
 */
public interface AppOrderPageService {

    OrderPageDTO selectOrderShopInfo( String shopId);

    List<OrderToRecommendDTO> selectRecommend(String shopId);

    List<AllDishDTO> selectAllDish(String shopId);
}
