package com.cqut.czb.bn.api.controller.food;

import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.InputRecommendDishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.SearchInputDTO;
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

    @GetMapping("/getRecommendDishesList")
    public JSONResult getRecommendDishesList(InputRecommendDishDTO inputRecommendDishDTO) {
        return new JSONResult(appDishService.getRecommendDishList(inputRecommendDishDTO));
    }

    @GetMapping("/getAllDishShopList")
    public JSONResult getAllDishShopList(DishShopDTO shop) {
        return new JSONResult(appDishService.getAllDishShop(shop));
    }

    @GetMapping("/searchDishShopByName")
    public JSONResult searchDishShopByName(SearchInputDTO searchInputDTO) {
        return new JSONResult(appDishService.searchDishShop(searchInputDTO));
    }
}
