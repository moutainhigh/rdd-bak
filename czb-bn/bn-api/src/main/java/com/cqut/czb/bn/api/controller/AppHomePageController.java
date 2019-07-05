package com.cqut.czb.bn.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
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
@RequestMapping("/api/AppHomePage")
public class AppHomePageController {

    @Autowired
    AppHomePageService appHomePageService;

    @Autowired
    IDictService iDictService;
    /**
     * app广告展示
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement",method = RequestMethod.GET)
    public JSONResult selectAnnouncement(){
        return new JSONResult(appHomePageService.selectAnnouncement());
    }

    /**
     * app油价播报
     * @return
     */
    @RequestMapping(value = "/selectPetrolPriceReport",method = RequestMethod.GET)
    public JSONResult selectPetrolPriceReport(@RequestParam(name="area") String area){
        return new JSONResult(appHomePageService.selectPetrolPriceReport(area));
    }

    /**
     * app企业套餐，对应服务套餐表
     * @return
     */
    @RequestMapping(value = "/selectServicePlan",method = RequestMethod.GET)
    public JSONResult selectServicePlan(){
        return new JSONResult(appHomePageService.selectServicePlan());
    }

    /**
     * app油卡专区，查找出对应油卡表未售出的油卡，以及获取保存未售出油卡
     * @return
     */
    @RequestMapping(value = "/selectPetrolZone",method = RequestMethod.GET)
    public JSONResult selectPetrol(@RequestParam(name="area") String area){
        System.out.println(area);
        if(area==null)
            return new JSONResult("无法获取当前位置", ResponseCodeConstants.FAILURE);
        return new JSONResult(appHomePageService.selectPetrolZone(area));
    }

    /**
     * 首页菜单路由获取  0国通，1中石油，2中石化
     */
    @RequestMapping(value ="/selectHomePageRouters",method = RequestMethod.GET)
    public JSONResult selectHomePageRouters( AppRouterDTO appRouterDTO){
        return new JSONResult(appHomePageService.selectHomePageRouters(appRouterDTO));
    }

    /**
     * 油卡信息获取
     */
    @RequestMapping(value ="/selectPetrolInfoDTO",method = RequestMethod.GET)
    public JSONResult selectPetrolInfoDTO(){
        return new JSONResult(appHomePageService.selectPetrolInfoDTO());
    }
}
