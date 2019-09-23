package com.cqut.czb.bn.api.controller.food;

import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.AppDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appDish")
public class AppDishController {

    @Autowired
    AppDishService appDishService;

    @GetMapping("getRecommendDishesList")
    public JSONResult getRecommendDishesList() {
        return new JSONResult(appDishService.getRecommendDishList());
    }

    @GetMapping("getAllDishShopList")
    public JSONResult getAllDishShopList(DishShopDTO shop) {
        return new JSONResult(appDishService.getAllDishShop(shop));
    }
}
