package com.cqut.czb.bn.api.controller.payBackNew.weChat;

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
@RequestMapping("/WeChatPayment")
public class WeChatPaymentController {

    @Autowired
    OrderPaymentService orderPaymentService;

    /**
     * 微信购买积分
     * @param request
     * @param response
     */
    @RequestMapping(value="/weChatBuyIntegral", method= RequestMethod.POST)
    public synchronized void wechatBuyIntegral(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("微信回调——购买积分");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().print(orderPaymentService.WeChatOrderPaymentNotify(request,"Integral"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 小程序库存商品支付
     * @param request
     * @param response
     */
    @RequestMapping(value="/verifyAppletPaymentWeChat", method= RequestMethod.POST)
    public synchronized void verifyAppletPaymentWeChat(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().write(orderPaymentService.WeChatOrderPaymentNotify(request,"AppletPayment"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信权益商品购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/weChatBuyEquityGoods", method= RequestMethod.POST)
    public synchronized void weChatBuyEquityGoods(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("微信回调——购买权益商品");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().print(orderPaymentService.WeChatOrderPaymentNotify(request,"EquityGoods"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
