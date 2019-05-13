package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/23
 * 作用：购买油卡
 */
@RestController
@RequestMapping("/AppBuyPetrol")
public class AppBuyPetrolController {

    @Autowired
    private  AppBuyPetrolService appBuyPetrolService;

    @Autowired
    private AppHomePageService appHomePageService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/buyPetrol",method = RequestMethod.POST)
    public JSONResult buyPetrol(Principal principal,@RequestBody PetrolInputDTO petrolInputDTO){
        User user = (User)redisUtils.get(principal.getName());
        petrolInputDTO.setUserAccount(user.getUserAccount());
        petrolInputDTO.setOwnerId(user.getUserId());
        //防止数据为空
        if(petrolInputDTO==null||user==null){
            new JSONResult(ResponseCodeConstants.FAILURE, "申请数据有误");
        }
        //检测是否有未完成的订单
        boolean isHave=  PetrolCache.isContainsNotPay(user.getUserId());
        if(!isHave){
            new JSONResult(ResponseCodeConstants.FAILURE, "存在未完成的订单");
        }

        //检测今日是否已经购买了油卡或充值
        boolean isTodayHadBuy=appBuyPetrolService.isTodayHadBuy(user);
        if(isTodayHadBuy){//true
            new JSONResult(ResponseCodeConstants.FAILURE, "今日已经购买了油卡");
        }

        //处理购油或充值
        String BuyPetrol=appBuyPetrolService.PurchaseControl(petrolInputDTO);
        if(BuyPetrol==null){
            new JSONResult(ResponseCodeConstants.FAILURE, "无法生成订单");
        }
        return new JSONResult(BuyPetrol);
    }

}
