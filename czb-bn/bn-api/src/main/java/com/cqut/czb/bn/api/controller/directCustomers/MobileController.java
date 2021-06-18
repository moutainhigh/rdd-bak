package com.cqut.czb.bn.api.controller.directCustomers;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/mobile")
public class MobileController {
    @Autowired
    PrepaidRefillService prepaidRefillService;


    @GetMapping("/telorder")
    public JSONResult getCardNum(DirectChargingOrderDto directChargingOrderDto){
        return null;
    }
}
