package com.cqut.czb.bn.api.controller.verifyAsynNoticeInfo.luPay;

import com.cqut.czb.bn.service.PaymentProcess.OrderVerifyService;
import com.cqut.czb.bn.service.thirdPartyCallBack.LuPay.petrolCoupons.VerifyAsynLuPayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/luPayVerifyAsyn")
public class LuPayVerifyAsynNoticeInfoController {
    @Resource(name= "orderVerifyServiceImpl")
    private OrderVerifyService paymentRecordService;

    @Autowired
    VerifyAsynLuPayInfoService verifyAsynLuPayInfoService;

    /**
     * 油卡优惠券回调璐付
     */
    @RequestMapping(value="/verifyPetrolCouponsInfo", method= RequestMethod.POST)
    public synchronized void verifyPetrolCouponsInfo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("璐付回调");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().print(verifyAsynLuPayInfoService.VerifyInfo(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/verifyPetrolCouponsInfoAiHu", method= RequestMethod.POST)
    public synchronized void verifyPetrolRechargeInfoAiHu(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——购买中石油优惠券");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().print(paymentRecordService.AliOrderPayNotify(request,"PetrolCoupons"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
