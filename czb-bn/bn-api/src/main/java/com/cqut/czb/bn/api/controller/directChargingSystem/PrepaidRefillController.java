package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prepaidRefill")
public class PrepaidRefillController {
    @Autowired
    PrepaidRefillService prepaidRefillService;
}
