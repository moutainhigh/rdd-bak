package com.cqut.czb.bn.api.controller.directCustomers;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import com.cqut.czb.bn.service.directCustomer.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/mobile")
public class MobileController {
    @Autowired
    MobileService mobileService;


    // 话费充值提交文档
    @PostMapping("/telorder")
    public JSONResult telorder(DirectCustomersDto directCustomersDto){
        return mobileService.telorder(directCustomersDto);
    }

    // 话费订单状态查询文档
    @GetMapping("/ordersta")
    public JSONResult ordersta(DirectCustomersDto directCustomersDto){
        return null;
    }
}
