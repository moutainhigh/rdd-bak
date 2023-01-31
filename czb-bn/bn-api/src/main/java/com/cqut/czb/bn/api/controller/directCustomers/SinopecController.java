package com.cqut.czb.bn.api.controller.directCustomers;

import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomer.MobileService;
import com.cqut.czb.bn.service.directCustomer.SinopecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sinopec")
public class SinopecController {
    @Autowired
    SinopecService sinopecService;


    // 个人卡充值提交文档
    @PostMapping("/onlineorder")
    public JSONResult onlineorder(DirectCustomersDto directCustomersDto){
        return sinopecService.onlineorder(directCustomersDto);
    }

    // 个人卡订单状态查询文档
    @GetMapping("/ordersta")
    public JSONResult ordersta(DirectCustomersDto directCustomersDto){
//        return sinopecService.ordersta(directCustomersDto);
        return null;
    }
}
