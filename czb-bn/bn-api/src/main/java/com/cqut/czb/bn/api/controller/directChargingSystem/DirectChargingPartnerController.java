package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 获取直充合伙人详情
     * @param directChargingOrderDto
     * @return
     */
    @GetMapping("/getDirectChargingPartnerOrder")
    public JSONResult getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto) {
        return new JSONResult(directChargingPartnerService.getDirectChargingPartnerOrder(directChargingOrderDto));
    }

    /**
     * 获取总额
     * @return
     */
    @GetMapping("/getTotalRechargeAmount")
    public JSONResult getTotalRechargeAmount() {
        return directChargingPartnerService.getTotalRechargeAmount();
    }

    /**
     * 获取个人的总额
     */
    @GetMapping("/getUserTotalRechargeAmount")
    public JSONResult getUserTotalRechargeAmount(DirectChargingOrderDto directChargingOrderDto) {
        return directChargingPartnerService.getUserTotalRechargeAmount(directChargingOrderDto);
    }

    /**
     * 删除直充合伙人
     * @param user
     * @return
     */
    @PostMapping("/deleteDirectChargingPartnerOrder")
    public JSONResult deleteDirectChargingPartnerOrder(User user) {
        return directChargingPartnerService.deleteDirectChargingPartnerOrder(user);
    }
}
