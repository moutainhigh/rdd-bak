package com.cqut.czb.bn.api.controller.food.AppOrderPage;

import com.cqut.czb.bn.entity.dto.food.AppOrderPage.InputOrderPageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.AppOrderPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * app 点餐页面
 */
@RestController
@RequestMapping("/api/OrderFood")
public class AppOrderPageController {

    @Autowired
    AppOrderPageService appOrderPageService;

//  获取商家的信息
    @GetMapping(value = "/getShopInfo")
    public JSONResult getShopInfo(InputOrderPageDTO inputOrderPageDTO){
        return new JSONResult(appOrderPageService.selectOrderShopInfo(inputOrderPageDTO));
    }

//    获取推荐的餐品
    @GetMapping(value = "/getRecommendation")
    public JSONResult getRecommendation(@RequestParam(value = "shopId") String shopId){
        return new JSONResult(appOrderPageService.selectRecommend(shopId));
    }

//    获取所有的商品
    @GetMapping(value = "/getAllDish")
    public JSONResult getAllDish(@RequestParam(value = "shopId") String shopId){
        return new JSONResult(appOrderPageService.selectAllDish(shopId));
    }




}
