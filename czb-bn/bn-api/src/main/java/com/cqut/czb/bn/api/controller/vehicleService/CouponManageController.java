package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.CouponManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * app用户优惠券列表获取
     * @param principal
     * @return
     */
    @GetMapping("/getCouponList")
    public JSONResult getCouponList(ServerCouponDTO serverCouponDTO, Principal principal){
        if (principal.getName()==null){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(couponManageService.getCouponList(serverCouponDTO,user));
    }

    /**
     * 后台获取优惠券标准列表
     * @param couponStandard
     * @param pageDTO
     * @return
     */
    @GetMapping("/selectAllCoupon")
    public JSONResult selectAllCoupon(CouponStandard couponStandard,PageDTO pageDTO){
        return new JSONResult(couponManageService.getAllCoupon(couponStandard,pageDTO));
    }

    /**
     * 后台新增优惠券标准
     * @param couponStandard
     * @return
     */
    @PostMapping("/insertCoupon")
    public JSONResult insertCoupon(CouponStandard couponStandard){
        return new JSONResult(couponManageService.insertCouponStandard(couponStandard));
    }

    /**
     * 后台修改优惠券
     * @param couponStandard
     * @return
     */
    @PostMapping("/updateCoupon")
    public JSONResult updateCoupon(CouponStandard couponStandard){
        return new JSONResult(couponManageService.updateCouponStandard(couponStandard));
    }

    @PostMapping("/deleteCoupon")
    public JSONResult deleteCoupon(CouponStandard couponStandard) {
        return new JSONResult(couponManageService.deleteCouponStandard(couponStandard));
    }
}
