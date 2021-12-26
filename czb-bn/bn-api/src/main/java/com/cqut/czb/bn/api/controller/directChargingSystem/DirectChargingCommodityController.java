package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/directChargingCommodityController")
public class DirectChargingCommodityController {
    @Autowired
    DirectChargingCommodityService directChargingCommodityService;

    @GetMapping("/getAllCommodity")
    public JSONResult getAllCommodity(PageDTO pageDTO) {
        return new JSONResult(directChargingCommodityService.getAllCommodity(pageDTO));
    }

    @GetMapping("/getElectricityCommodity")
    public JSONResult getElectricityCommodity(PageDTO pageDTO) {
        return new JSONResult(directChargingCommodityService.getElectricityCommodity(pageDTO));
    }
}
