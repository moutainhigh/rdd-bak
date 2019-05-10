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
        if(petrolInputDTO==null||user==null){
            System.out.println("user"+user.getUserAccount());
            System.out.println("user"+user.getUserId());
            System.out.println("getPetrolKind"+petrolInputDTO.getPetrolKind());
            System.out.println("getPetrolPrice"+petrolInputDTO.getPetrolPrice());
            new JSONResult(ResponseCodeConstants.FAILURE, "申请数据有误");
        }
        //检测是否有未完成的订单
        boolean isHave=  PetrolCache.isContainsNotPay(user.getUserId());
        if(!isHave){
            new JSONResult(ResponseCodeConstants.FAILURE, "存在未完成的订单");
        }
        //随机获取一张卡
        petrolInputDTO.setUserAccount(user.getUserAccount());
        petrolInputDTO.setOwnerId(user.getUserId());
                System.out.println("AllpetrolMap.size():"+ PetrolCache.AllpetrolMap.size());
                System.out.println("currentPetrolMap.size():"+PetrolCache.currentPetrolMap.size());
        Petrol petrol=PetrolCache.randomPetrol(petrolInputDTO);
                System.out.println("********************************");
                System.out.println("2AllpetrolMap.size():"+PetrolCache.AllpetrolMap.size());
                System.out.println("2currentPetrolMap.size():"+PetrolCache.currentPetrolMap.size());
        if(petrol==null)
            return new JSONResult(ResponseCodeConstants.FAILURE, "油卡申请失败，无此类油卡");
        String BuyPetrol=appBuyPetrolService.BuyPetrol(petrol,petrolInputDTO);
        return new JSONResult(BuyPetrol);
    }

}
