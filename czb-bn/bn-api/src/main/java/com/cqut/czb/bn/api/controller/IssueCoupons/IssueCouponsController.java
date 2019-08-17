package com.cqut.czb.bn.api.controller.IssueCoupons;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.util.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/issueCoupons")
public class IssueCouponsController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/getInputInfo",method = RequestMethod.GET)
    public JSONResult getInputInfo(Principal principal){
        User user = (User) redisUtils.get(principal.getName());
        return null;
    }


}
