package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.AppJoinUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/joinUs")
public class AppJoinUsController {

    @Autowired
    AppJoinUsService appJoinUsService;
    @Autowired
    RedisUtils redisUtils;

    @PostMapping("/toBecomeRider")
    public JSONResult toBecomeRider(CleanRider cleanRider, Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appJoinUsService.toBecomeRider(cleanRider,user));
    }

    @PostMapping("/toBecomeShop")
    public JSONResult toBecomeShop(ShopDTO shopDTO, Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appJoinUsService.toBecomeShop(shopDTO,user));
    }
}
