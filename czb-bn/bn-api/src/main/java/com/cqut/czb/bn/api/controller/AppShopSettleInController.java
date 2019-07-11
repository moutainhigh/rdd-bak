package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
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
     * app商家入驻广告展示
     *
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement", method = RequestMethod.GET)
    public JSONResult selectAnnouncement(@RequestParam(name = "locationCode") String locationCode) {
        return new JSONResult(appHomePageService.selectAnnouncement(locationCode));
    }

    /**
     * app商家入驻路由配置
     */
    @RequestMapping(value = "/selectShopSettleInRouters", method = RequestMethod.GET)
    public JSONResult selectHomePageRouters(AppRouterDTO appRouterDTO) {
        return new JSONResult(appHomePageService.selectHomePageRouters(appRouterDTO));
    }

    /**
     * app获取各个标签的展示商品
     */
    @RequestMapping(value = "/selectGoods", method = RequestMethod.GET)
    public JSONResult selectGoods(@Param("classification") String classification) {
        return new JSONResult(appShopSettleInService.selectCommodity(classification));
    }

}
