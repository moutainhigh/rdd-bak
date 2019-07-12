package com.cqut.czb.bn.api.controller;

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
     * app's Advertising display for merchants to enter
     *
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement", method = RequestMethod.GET)
    public JSONResult selectAnnouncement(@RequestParam(name = "locationCode") String locationCode) {
        return new JSONResult(appHomePageService.selectAnnouncement(locationCode));
    }

    /**
     * app's Get display items for each navigation item
     */
    @RequestMapping(value = "/selectGoods", method = RequestMethod.GET)
    public JSONResult selectGoods(@Param("classification") String classification) {
        return new JSONResult(appShopSettleInService.selectCommodity(classification));
    }

    /**
     * app's Get all the goods information
     */
    @RequestMapping(value = "/selectAllGoods", method = RequestMethod.GET)
    public JSONResult selectAllGoods(@Param("classification") String classification) {
        return new JSONResult(appShopSettleInService.selectAllCommodity(classification));
    }


    /**
     * app's Get Service details information
     */
    @RequestMapping(value = "/selectServiceDetails", method = RequestMethod.GET)
    public JSONResult selectServiceDetails(@Param("commodityId") String commodityId) {
        return new JSONResult(appShopSettleInService.selectServiceDetails(commodityId));
    }

}
