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
import java.util.Map;

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
//    public JSONResult buyPetrol(PetrolInputDTO petrolInputDTO){
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserAccount("15870596710");
//        user.setUserId("155786053583269");
        petrolInputDTO.setUserAccount(user.getUserAccount());
        petrolInputDTO.setOwnerId(user.getUserId());
        //防止数据为空
        if(petrolInputDTO==null||user==null){
            return new JSONResult("申请数据有误", ResponseCodeConstants.FAILURE);

        }

        //检测是否有未完成的订单
        boolean isHave=  PetrolCache.isContainsNotPay(user.getUserId());
        if(!isHave){
            return new JSONResult("存在未完成的订单", ResponseCodeConstants.FAILURE);
        }

        //检测今日是否已经购买了油卡或充值
        boolean isTodayHadBuy=appBuyPetrolService.isTodayHadBuy(petrolInputDTO);
        if(isTodayHadBuy){//true
            return new JSONResult("今日已购买油卡或充值相应类型油卡，请明日再来", ResponseCodeConstants.FAILURE);
        }

        //处理购油或充值
        Map<String,String> BuyPetrol=appBuyPetrolService.PurchaseControl(petrolInputDTO);
        if(BuyPetrol==null){
            return new JSONResult("无法生成订单",ResponseCodeConstants.FAILURE);
        }else {
           if(BuyPetrol.get("-1")!=null){
               return  new JSONResult(BuyPetrol.get("-1"),ResponseCodeConstants.FAILURE);
           }else if(BuyPetrol.get("0")!=null){
               return  new JSONResult("购买成功",200,BuyPetrol.get("0"));
           }else if(BuyPetrol.get("2")!=null){
               return  new JSONResult("充值成功",200,BuyPetrol.get("2"));
           }else {
               return new JSONResult("无法生成订单",ResponseCodeConstants.FAILURE);
           }
        }

//        return new JSONResult("购买成功",ResponseCodeConstants.SUCCESS,BuyPetrol);
//        return new JSONResult(BuyPetrol);
    }

}
