package com.cqut.czb.bn.service.appPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.CleanServerVehicleDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.InputDishDTO;
import com.cqut.czb.bn.entity.entity.User;

import java.util.Map;

public interface AppBuyDishService {

    Map  AliBuyDish(User user, InputDishDTO inputDishDTO);

    Map  WeChatBuyDish(User user,InputDishDTO inputDishDTO);

}
