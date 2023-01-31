package com.cqut.czb.bn.api.controller.directCustomers;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import com.cqut.czb.bn.service.directCustomer.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
//        return mobileService.ordersta(directCustomersDto);
        return null;
    }

    // 话费订单状态查询文档
    @GetMapping("/getState")
    public JSONResult getState(DirectCustomersDto directCustomersDto){
//        return mobileService.getState(directCustomersDto);
        return null;
    }

    // 余额查询文档
    @GetMapping("/getTheBalance")
    public JSONResult getTheBalance(DirectCustomersDto directCustomersDto){
        return mobileService.getTheBalance(directCustomersDto);
    }

    //话费第三方回调
    @RequestMapping(value="/DirectPhone", method= RequestMethod.POST)
    public synchronized JSONResult DirectPhone(HttpServletRequest request, HttpServletResponse response){
        System.out.println("话费第三方回调");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
//        try {
//            response.getWriter().print(mobileService.DirectPhone(request));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return mobileService.DirectPhone(request);
    }
}
