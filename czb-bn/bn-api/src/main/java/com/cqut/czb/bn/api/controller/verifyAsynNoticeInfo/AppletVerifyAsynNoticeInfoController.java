package com.cqut.czb.bn.api.controller.verifyAsynNoticeInfo;

import com.cqut.czb.bn.service.PaymentProcess.OrderVerifyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/AppletVerifyAsyn")
public class AppletVerifyAsynNoticeInfoController {

    @Resource(name= "orderVerifyServiceImpl")
    private OrderVerifyService paymentRecordService;

    /**
     * 小程序支付
     * @param request
     * @param response
     */
    @RequestMapping(value="/verifyAppletWeChat", method= RequestMethod.POST)
    public synchronized void verifyAppletWeChat(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().write(paymentRecordService.WeChatOrderPayNotify(request,"Applet"));
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
        response.setContentType("text/xml");
        try {
            response.getWriter().write(paymentRecordService.WeChatOrderPayNotify(request,"AppletPayment"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 小程序Vip支付
     * @param request
     * @param response
     */
    @RequestMapping(value="/verifyAppletRechargeVip", method= RequestMethod.POST)
    public synchronized void verifyAppletRechargeVip(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().write(paymentRecordService.WeChatOrderPayNotify(request,"RechargeVip"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
