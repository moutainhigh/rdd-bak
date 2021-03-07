package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 侯家领
 * 直充合伙人管理 directChargingPartner
 * 创建时间： 2021/3/07
 */
@RestController
@RequestMapping("/api/directChargingPartnerController")
public class DirectChargingPartnerController {
    @Autowired
    DirectChargingPartnerService directChargingPartnerService;

    /**
     * 分页查询直充管理人
     * @param directChargingOrderDto
     * @return
     */
    @GetMapping("/getDirectChargingPartnerList")
    public JSONResult getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto) {
        return new JSONResult(directChargingPartnerService.getDirectChargingPartnerList(directChargingOrderDto));
    }

    @GetMapping("/getDirectChargingPartnerOrder")
    public JSONResult getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto) {
        return new JSONResult(directChargingPartnerService.getDirectChargingPartnerOrder(directChargingOrderDto));
    }

    @GetMapping("/getTotalRechargeAmount")
    public JSONResult getTotalRechargeAmount() {
        return directChargingPartnerService.getTotalRechargeAmount();
    }
}
