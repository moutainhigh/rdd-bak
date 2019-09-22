package com.cqut.czb.bn.api.controller.food.OrderFood;

import com.cqut.czb.bn.entity.global.JSONResult;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/OrderFood")
public class OrderFoodController {

    @GetMapping(value = "/getShopInfo")
    public JSONResult getShopInfo(@RequestParam(value = "shopId") String shopId){

        return null;
    }


}
