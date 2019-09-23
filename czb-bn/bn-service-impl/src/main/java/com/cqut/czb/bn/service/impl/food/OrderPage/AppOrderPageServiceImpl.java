package com.cqut.czb.bn.service.impl.food.OrderPage;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.AllDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderPageDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderToRecommendDTO;
import com.cqut.czb.bn.service.food.AppOrderPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppOrderPageServiceImpl implements AppOrderPageService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    DishMapperExtra dishMapperExtra;

    @Override
    public OrderPageDTO selectOrderShopInfo(String shopId) {
        OrderPageDTO orderPageDTO=new OrderPageDTO();
        orderPageDTO=shopMapperExtra.selectOrderShopInfo(shopId);
        orderPageDTO.setTopImg(shopMapperExtra.selectImg(shopId));
        return orderPageDTO;
    }

    @Override
    public List<OrderToRecommendDTO> selectRecommend(String shopId) {
        return dishMapperExtra.selectRecommend(shopId);
    }

    @Override
    public List<AllDishDTO> selectAllDish(String shopId) {
        return dishMapperExtra.selectAllDish(shopId);
    }

}
