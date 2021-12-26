package com.cqut.czb.bn.api.controller.electricityRecharge;

import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomer.MobileService;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/electricity")
public class ElectricityController {

    @Autowired
    ElectricityRechargeService electricityRechargeService;

    // 话费充值提交文档
    @PostMapping("/elcorder")
    public JSONResult elcorder(ElectricityDto electricityDto){
        return electricityRechargeService.elcOrder(electricityDto);
    }

}
