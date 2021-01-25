package com.cqut.czb.bn.api.controller.directChargingSystem;


import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/AliPayReturn")
public class AliPayReturnController {
    @Autowired
    PrepaidRefillService prepaidRefillService;

    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

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

    @GetMapping("/getGoodsPrice")
    public JSONResult getGoodsPrice(Integer type) {
        return new JSONResult(prepaidRefillService.getGoodsPrice(type));
    }
}
