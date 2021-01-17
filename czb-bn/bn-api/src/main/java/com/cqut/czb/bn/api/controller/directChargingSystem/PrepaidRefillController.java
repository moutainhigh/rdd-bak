package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/prepaidRefill")
public class PrepaidRefillController {
    @Autowired
    PrepaidRefillService prepaidRefillService;

    @GetMapping("/getGoodsPrice")
    public JSONResult getGoodsPrice(Integer type) {
        return new JSONResult(prepaidRefillService.getGoodsPrice(type));
    }
}
