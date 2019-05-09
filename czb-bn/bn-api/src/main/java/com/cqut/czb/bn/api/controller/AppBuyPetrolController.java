package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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
        if(petrolInputDTO==null||principal==null)
            new JSONResult(ResponseCodeConstants.FAILURE, "申请数据有误");
        User user = (User)redisUtils.get(principal.getName());
        petrolInputDTO.setOwnerId(user.getUserId());
        petrolInputDTO.setUserAccount(user.getUserAccount());
        //暂时先调用
        System.out.println("油卡刷新"+appHomePageService.selectPetrolZone());
        //随机获取一张卡
        AllPetrolDTO allPetrolDTO=new AllPetrolDTO();
        System.out.println("AllpetrolMap.size():"+allPetrolDTO.getAllpetrolMap().size());
        System.out.println("currentPetrolMap.size():"+allPetrolDTO.getCurrentPetrolMap().size());
        Petrol petrol=AllPetrolDTO.randomPetrol(petrolInputDTO);
        System.out.println("********************************");
        System.out.println("2AllpetrolMap.size():"+allPetrolDTO.getAllpetrolMap().size());
        System.out.println("2currentPetrolMap.size():"+allPetrolDTO.getCurrentPetrolMap().size());
        if(petrol==null)
            return new JSONResult(ResponseCodeConstants.FAILURE, "油卡申请失败，无此类油卡");
        String BuyPetrol=appBuyPetrolService.BuyPetrol(petrol,petrolInputDTO);
        return new JSONResult(BuyPetrol);
    }

}
