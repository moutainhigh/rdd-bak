package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.dao.mapper.PetrolMapper;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
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
    AppBuyPetrolService appBuyPetrolService;

    @RequestMapping(value = "/buyPetrol",method = RequestMethod.POST)
    public JSONResult buyPetrol(Petrol petrol){
        //此处的为死数据
        // 油卡购买记录表
        PetrolSalesRecords petrolSalesRecords=new PetrolSalesRecords();
        petrolSalesRecords.setBuyerId(petrol.getOwnerId());
        petrolSalesRecords.setPetrolId(petrol.getPetrolId());
        //收款记录表
        DepositRecords depositRrecords=new DepositRecords();
        //用户收益信息
        UserIncomeInfo userIncomeInfo=new UserIncomeInfo();

        //更改油卡状态
        boolean updatePetrol=appBuyPetrolService.updatePetrol(petrol);
        //插入油卡购买记录表
        boolean insertPetrolSalesRecords=appBuyPetrolService.insertPetrolSalesRecords(petrolSalesRecords);
        //新增收款记录表
        boolean insertDepositRrecords=appBuyPetrolService.insertDepositRecords(depositRrecords);

        //新增用户收益信息表——返佣
        boolean insertUserIncomeInfo=appBuyPetrolService.insertUserIncomeInfo(userIncomeInfo);

        if(updatePetrol==insertPetrolSalesRecords==insertDepositRrecords==insertPetrolSalesRecords) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "新增成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "新增失败");
        }
    }
}
