package com.cqut.czb.bn.api.controller.directCustomers;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import com.cqut.czb.bn.service.directCustomer.CustomerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customerManage")
public class CustomerManageController {
    @Autowired
    CustomerManageService customerManageService;

    @GetMapping("/getCustomers")
    public JSONResult getCustomers(CustomerManageDto customerManageDto){
        return customerManageService.getCustomers(customerManageDto);
    }

    @GetMapping("/recharge")
    public JSONResult recharge(CustomerManageDto customerManageDto){
        return customerManageService.recharge(customerManageDto);
    }

    @GetMapping("/recovered")
    public JSONResult recovered(CustomerManageDto customerManageDto){
        return customerManageService.recovered(customerManageDto);
    }
}
