package com.cqut.czb.bn.api.controller.integral;

import com.cqut.czb.bn.service.integral.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/WechatBuyIntegral")
public class WechatBuyIntegralController {

    @Autowired
    IntegralService integralService;

    /**
     * 微信购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/wechatBuyIntegral", method= RequestMethod.POST)
    public synchronized void wechatBuyIntegral(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("微信回调——购买积分");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().print(integralService.wechatBuyIntegral(request,"Integral"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
