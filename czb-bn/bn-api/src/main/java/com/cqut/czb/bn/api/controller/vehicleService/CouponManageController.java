package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.IssueServerCouponDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
import com.cqut.czb.bn.service.vehicleService.CouponManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    ServerOrderServiceImpl serverOrderService;

    /**
     * app用户优惠券列表获取
     * @param principal
     * @return
     */
    @GetMapping("/getCouponList")
    public JSONResult getCouponListApp(ServerCouponDTO serverCouponDTO, Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(couponManageService.getCouponList(serverCouponDTO,user));
    }

    /**
     * app用户可使用的优惠券列表获取
     * @param principal
     * @return
     */
    @GetMapping("/getUseCouponList")
    public JSONResult getUseCouponList(ServerCouponDTO serverCouponDTO, Principal principal){
        if (principal ==null || principal.getName()==null ){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(couponManageService.getUseCouponList(serverCouponDTO,user));
    }

    /**
     * /后台优惠券列表获取
     * @param serverCouponDTO
     * @return
     */
    @GetMapping("/getCouponListPc")
    public JSONResult getCouponListPc(ServerCouponDTO serverCouponDTO,PageDTO pageDTO){
        return new JSONResult(couponManageService.getCouponByUser(serverCouponDTO,pageDTO));
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
    @PermissionCheck(role = "管理员")
    public JSONResult insertCoupon(CouponStandard couponStandard){
        return new JSONResult(couponManageService.insertCouponStandard(couponStandard));
    }

    /**
     * 后台修改优惠券标准
     * @param couponStandard
     * @return
     */
    @PostMapping("/updateCoupon")
    @PermissionCheck(role = "管理员")
    public JSONResult updateCoupon(CouponStandard couponStandard){
        return new JSONResult(couponManageService.updateCouponStandard(couponStandard));
    }

    /**
     * 根据id删除优惠券标准
     * @param couponStandard
     * @return
     */
    @PostMapping("/deleteCoupon")
    @PermissionCheck(role = "管理员")
    public JSONResult deleteCoupon(CouponStandard couponStandard) {
        return new JSONResult(couponManageService.deleteCouponStandard(couponStandard));
    }

    /**
     * 得到优惠券标准的所有类型
     * @return
     */
    @GetMapping("/getCouponType")
    public JSONResult getCouponType(){
        return new JSONResult(couponManageService.getCouponType());
    }

    /**
     * 发放优惠券
     * @param issueServerCouponDTO
     * @return
     */
    @PostMapping("issueCoupon")
    @PermissionCheck(role = "管理员")
    public JSONResult issueCoupon(IssueServerCouponDTO issueServerCouponDTO){
        return new JSONResult(couponManageService.issueCoupon(issueServerCouponDTO));
    }
}
