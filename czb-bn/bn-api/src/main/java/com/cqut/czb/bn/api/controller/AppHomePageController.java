package com.cqut.czb.bn.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cqut.czb.bn.entity.entity.AppRouter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 创建者：陈德强
 * 作用：手机app公告管理，对应公告表：announcement
 * 创建时间：2019/4/20
 */
@RestController
@RequestMapping("/AppHomePage")
public class AppHomePageController {

    @Autowired
    AppHomePageController appHomePageController;

    @Autowired
    AppHomePageService appHomePageService;

    //测试
    @Autowired
    IDictService dictService;


    /**
     * app广告展示
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement",method = RequestMethod.GET)
    public JSONResult selectAnnouncement(){
        return new JSONResult<List<appAnnouncementDTO>>(appHomePageService.selectAnnouncement());
    }

    /**
     * app油价播报，对应油卡销售地域配置表
     * @return
     */
    @RequestMapping(value = "/selectPetrolSaleConfig",method = RequestMethod.GET)
    public JSONResult selectPetrolSaleConfig(){
        return new JSONResult<List<PetrolSaleConfig>>(appHomePageService.selectPetrolSaleConfig());
    }

    /**
     * app企业套餐，对应服务套餐表
     * @return
     */
    @RequestMapping(value = "/selectServicePlan",method = RequestMethod.GET)
    public JSONResult selectServicePlan(){
        return new JSONResult<List<ServicePlan>>(appHomePageService.selectServicePlan());
    }

    /**
     * app油卡专区，查找出对应油卡表未售出的油卡，以及获取保存未售出油卡
     * @return
     */
    @RequestMapping(value = "/selectPetrolZone",method = RequestMethod.GET)
    public JSONResult selectPetrol(){
        return new JSONResult<List<PetrolZoneDTO>>(appHomePageService.selectPetrolZone());
    }

    /**
     * 首页菜单路由获取
     */
    @RequestMapping(value ="/selectHomePageRouters",method = RequestMethod.GET)
    public JSONResult selectHomePageRouters( AppRouter appRouter){
        return new JSONResult<List<AppRouter>>(appHomePageService.selectHomePageRouters(appRouter));
    }

    /**
     * 油卡信息获取
     */
    @RequestMapping(value ="/selectPetrolInfoDTO",method = RequestMethod.GET)
    public JSONResult selectPetrolInfoDTO(){
        Date date1 = new Date();
        DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
        System.out.println(df1.format(date1));
        Date date2=new Date();
        DateFormat dfe2 = DateFormat.getDateInstance();//日期格式，精确到日
        System.out.println(dfe2.format(date2).equals(df1.format(date1)));
        Double newIncome= 125.11;
        Double oldIncome=100.10;
        BigDecimal bnewIncome = new BigDecimal(newIncome);
        BigDecimal boldIncome = new BigDecimal(oldIncome);

        double i = bnewIncome.subtract(boldIncome).doubleValue();
        System.out.println(Double.parseDouble(String.format("%.2f", i)));
        IncomeLog incomeLog=new IncomeLog();
        incomeLog.setAmount(Double.parseDouble(String.format("%.2f", i)));
        System.out.println(incomeLog.getAmount());
        return new JSONResult<List<PetrolInfoDTO>>(appHomePageService.selectPetrolInfoDTO());
    }
}
