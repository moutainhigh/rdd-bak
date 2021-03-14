package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/directChargingOrderController")
public class DirectChargingOrderController {
    @Autowired
    DirectChargingOrderService directChargingOrderService;
    @PostMapping(value = "/updateRecord")
    public JSONResult updateRecord(DirectChargingOrderDto directChargingOrderDto) {
        return directChargingOrderService.updateRecord(directChargingOrderDto);
    }

    @GetMapping(value = "/getRecordByOrderId")
    public JSONResult getRecordByOrderId(String orderId) {
        return directChargingOrderService.getRecordByOrderId(orderId);
    }
}
