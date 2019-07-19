package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ShopSettledService;
import com.cqut.czb.bn.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api/shopSettled")
@RestController
public class ShopSettledController {

    @Autowired
    ShopSettledService shopSettledService;

    @Autowired
    RedisUtils redisUtil;

    @GetMapping("/getShopInfo")
    public JSONResult getShopInfo(ShopDTO shopDTO, Principal principal){
        User user = (User) redisUtil.get(principal.getName());
        return new JSONResult(shopSettledService.getShopInfo(shopDTO,user));
    }

    @PostMapping("/updateShopInfo")
    public JSONResult updateShopInfo(ShopDTO shopDTO){
        return new JSONResult(shopSettledService.updateShopInfo(shopDTO));
    }

    @GetMapping("/getCommodity")
    public JSONResult getCommodity(CommodityDTO commodityDTO,Principal principal,PageDTO pageDTO){
        User user = (User) redisUtil.get(principal.getName());
        if (user==null||"".equals(user.getUserId())){
            return new JSONResult();
        }
        return new JSONResult(shopSettledService.getCommodity(commodityDTO,pageDTO,user));
    }
}
