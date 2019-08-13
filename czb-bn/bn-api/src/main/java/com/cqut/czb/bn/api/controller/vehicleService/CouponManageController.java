package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.CouponManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 洗车优惠券管理
 */
@RestController
@RequestMapping("/api/couponManage")
public class CouponManageController {

    @Autowired
    CouponManageService couponManageService;
    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getCouponList")
    public JSONResult getCouponList(Principal principal){
        if (principal.getName()==null){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(couponManageService.getCouponList(user));
    }
}