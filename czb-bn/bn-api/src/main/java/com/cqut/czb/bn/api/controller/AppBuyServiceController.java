package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/AppBuyService")
public class AppBuyServiceController {

    /**
     * app buy service and input information(购买服务和录入信息)
     */
    @RequestMapping(value = "/buyService", method = RequestMethod.GET)
    public JSONResult buyService(@Param("commodityId") String commodityId) {
        return null;
    }
}
