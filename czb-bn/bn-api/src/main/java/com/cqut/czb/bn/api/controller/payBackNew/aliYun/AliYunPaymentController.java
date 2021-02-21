package com.cqut.czb.bn.api.controller.payBackNew.aliYun;

import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.service.paymentNew.paybackService.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/AliYunPayment")
public class AliYunPaymentController {

    @Autowired
    IntegralService integralService;

    @Autowired
    OrderPaymentService orderPaymentService;

    /**
     * 微信购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/aliYunBuyIntegral", method= RequestMethod.POST)
    public synchronized void aliYunBuyIntegral(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——购买积分");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().print(orderPaymentService.AliOrderPaymentNotify(request,"Integral"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * h5支付宝库存商品支付
     * @param request
     * @param response
     */
    @RequestMapping(value="/aliYunBuyCommodity", method= RequestMethod.POST)
    public synchronized void aliYunBuyCommodity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——购买积分");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().write(orderPaymentService.AliOrderPaymentNotify(request,"H5Commodity"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
