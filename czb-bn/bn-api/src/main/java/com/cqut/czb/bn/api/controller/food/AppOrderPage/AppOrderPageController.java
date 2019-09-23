package com.cqut.czb.bn.api.controller.food.AppOrderPage;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.AppOrderPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;


/**
 * app 点餐页面
 */
@RestController
@RequestMapping("/api/OrderFood")
public class AppOrderPageController {

    @Autowired
    AppOrderPageService appOrderPageService;


    @GetMapping(value = "/getShopInfo")
    public JSONResult getShopInfo(@RequestParam(value = "shopId") String shopId){
        return new JSONResult(appOrderPageService.selectOrderShopInfo(shopId));
    }

    @GetMapping(value = "/getRecommendation")
    public JSONResult getRecommendation(@RequestParam(value = "shopId") String shopId){

        return null;
    }


}
