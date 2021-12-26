package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/WechatPayReturn")
public class WechatPayReturnController {
    @Autowired
    PrepaidRefillService prepaidRefillService;

    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 微信购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/wechatPayReturn", method= RequestMethod.POST)
    public synchronized void aliPayReturn(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("微信回调——直冲");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().print(oilCardRechargeService.wechatPayReturn(request,"Direct"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/WeChatPayBackElectricity", method= RequestMethod.POST)
    public synchronized void weChatPayBackElectricity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("微信回调——水电费充值");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().print(oilCardRechargeService.wechatPayReturn(request,"Electricity"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
