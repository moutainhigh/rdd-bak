package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DetailsOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.entity.User;

import java.util.List;

public interface MyOrderService {
    List<DishOrderDTO> getAllMyOrderList(User user);

    DetailsOrderDTO getOrderDetails(DishOrderDTO dishOrderDTO);

    Boolean cancelDishOrder(DishOrderDTO dishOrderDTO);
}
