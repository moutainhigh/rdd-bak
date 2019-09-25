package com.cqut.czb.bn.service.appPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.CleanServerVehicleDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.InputDishDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface AppBuyDishService {

    String AliBuyDish(User user, InputDishDTO inputDishDTO);

    JSONObject WeChatBuyDish(User user,InputDishDTO inputDishDTO);

}
