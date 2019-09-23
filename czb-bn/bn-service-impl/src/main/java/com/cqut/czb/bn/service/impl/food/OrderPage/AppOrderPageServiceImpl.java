package com.cqut.czb.bn.service.impl.food.OrderPage;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.entity.dto.food.OrderPage.OrderPageDTO;
import com.cqut.czb.bn.service.food.AppOrderPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppOrderPageServiceImpl implements AppOrderPageService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Override
    public OrderPageDTO selectOrderShopInfo(String shopId) {
        OrderPageDTO orderPageDTO=new OrderPageDTO();
        orderPageDTO=shopMapperExtra.selectOrderShopInfo(shopId);
        orderPageDTO.setTopImg(shopMapperExtra.selectImg(shopId));
        return orderPageDTO;
    }

}
