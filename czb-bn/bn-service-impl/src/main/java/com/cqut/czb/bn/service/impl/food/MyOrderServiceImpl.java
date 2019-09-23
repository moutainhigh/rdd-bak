package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.food.DishOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.OrderDishesMapperExtra;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DetailsOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.OrderDishDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.food.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MyOrderServiceImpl implements MyOrderService {
    @Autowired
    DishOrderMapperExtra dishOrderMapperExtra;
    @Autowired
    OrderDishesMapperExtra orderDishesMapperExtra;

    @Override
    public List<DishOrderDTO> getAllMyOrderList(User user) {
        return dishOrderMapperExtra.selectOrderByUser(user.getUserId());
    }

    @Override
    public DetailsOrderDTO getOrderDetails(DishOrderDTO dishOrderDTO) {
        DetailsOrderDTO detailsOrder = dishOrderMapperExtra.selectOrderDetails(dishOrderDTO);
        detailsOrder.setDishes(orderDishesMapperExtra.selectDishInOrder(detailsOrder.getOrderId()));
        return detailsOrder;
    }
}
