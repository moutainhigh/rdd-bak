package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.AppShopSettleInService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author：陈德强
 * function：app商家入驻首页
 */
@RestController
@RequestMapping("/api/AppShopSettleIn")
public class AppShopSettleInController {
    @Autowired
    public AppHomePageService appHomePageService;

    @Autowired
    public AppShopSettleInService appShopSettleInService;

    /**
     * app's Advertising display for merchants to enter（商家入驻轮播图）
     *
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement", method = RequestMethod.GET)
    public JSONResult selectAnnouncement(@RequestParam(name = "locationCode") String locationCode) {
        return new JSONResult(appHomePageService.selectAnnouncement(locationCode));
    }

    /**
     * app's Get display items for each navigation item（获取单个导航栏信息）
     */
    @RequestMapping(value = "/selectGoods", method = RequestMethod.GET)
    public JSONResult selectGoods(PageDTO pageDTO, @Param("classification") String classification) {
        return new JSONResult(appShopSettleInService.selectCommodity(pageDTO,classification));
    }

    /**
     * app's Get all the goods information（获取多个导航栏信息）
     */
    @RequestMapping(value = "/selectAllGoods", method = RequestMethod.GET)
    public JSONResult selectAllGoods(@Param("classification") String classification) {
        return new JSONResult(appShopSettleInService.selectAllCommodity(classification));
    }


    /**
     * app's Get Service details information（获取商品具体的信息）
     */
    @RequestMapping(value = "/selectServiceDetails", method = RequestMethod.GET)
    public JSONResult selectServiceDetails(@Param("commodityId") String commodityId) {
        return new JSONResult(appShopSettleInService.selectServiceDetails(commodityId));
    }

    /**
     * app's Get the nav（获取导航栏）
     */
    @RequestMapping(value = "/selectShopSettleInNav", method = RequestMethod.GET)
    public JSONResult selectShopSettleInNav() {
        return new JSONResult(appShopSettleInService.selectShopSettleInNav());
    }

    /**
     * app's Get the information input（获取信息录入必填项）
     */
    @RequestMapping(value = "/getInputItem", method = RequestMethod.GET)
    public JSONResult getInputItem(@Param("commodityId") String commodityId) {
        return new JSONResult(appShopSettleInService.getInputItem(commodityId));
    }

}
