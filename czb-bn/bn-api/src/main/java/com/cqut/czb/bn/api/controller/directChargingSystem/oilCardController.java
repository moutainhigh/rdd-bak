package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/oilCard")
public class oilCardController {
    @Autowired
    PrepaidRefillService prepaidRefillService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getCardNum")
    public JSONResult getCardNum(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(prepaidRefillService.getInfoNum(user.getUserId()));
    }
}
