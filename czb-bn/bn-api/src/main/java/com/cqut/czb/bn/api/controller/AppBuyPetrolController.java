package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/buyPetrol",method = RequestMethod.POST)
    public JSONResult buyPetrol(@Validated @RequestBody PetrolInputDTO petrolInputDTO){
        //随机获取一张卡
        AllPetrolDTO allPetrolDTO=new AllPetrolDTO();
        Petrol petrol=allPetrolDTO.randomPetrol(petrolInputDTO);
        if(petrol==null)
            return new JSONResult(ResponseCodeConstants.FAILURE, "油卡申请失败，无此类油卡");
        String BuyPetrol=appBuyPetrolService.BuyPetrol(petrol,petrolInputDTO);

        return new JSONResult(BuyPetrol);
    }



}
