package com.cqut.czb.bn.api.controller.directChargingSystem;


import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/AliPayReturn")
public class AliPayReturnController {

    @Autowired
    OilCardRechargeService oilCardRechargeService;

    /**
     * 支付宝购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/aliPayReturn", method= RequestMethod.POST)
    public synchronized void aliPayReturn(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——直冲");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().print(oilCardRechargeService.aliPayReturn(request,"Direct"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
