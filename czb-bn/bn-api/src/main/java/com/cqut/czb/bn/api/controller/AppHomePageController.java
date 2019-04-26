package com.cqut.czb.bn.api.controller;


import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * app广告展示
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement",method = RequestMethod.GET)
    public JSONResult selectAnnouncement(){
        return new JSONResult<List<Announcement>>(appHomePageService.selectAnnouncement());
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
     * app油卡专区，对应油卡表未售出的油卡
     * @return
     */
    @RequestMapping(value = "/selectPetrolZone",method = RequestMethod.GET)
    public JSONResult selectPetrol(){
        return new JSONResult<List<PetrolZoneDTO>>(appHomePageService.selectPetrolZone());
    }

}
