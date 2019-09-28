package com.cqut.czb.bn.service.food.WebOrderService;

import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodOrder;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WebOrderService {
    JSONResult search(FoodOrder foodOrder, PageDTO pageDTO, User user);

    JSONResult sureOrder(String orderId);

    JSONResult cancelOrder(String orderId);
}
