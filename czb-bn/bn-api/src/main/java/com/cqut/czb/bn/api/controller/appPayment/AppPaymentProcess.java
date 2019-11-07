package com.cqut.czb.bn.api.controller.appPayment;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.dto.PaymentProcessDTO;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 支付异常线下处理
 */
@RestController
@RequestMapping("/api/AppPaymentProcess")
public class AppPaymentProcess {

    @Autowired
    AppHomePageService appHomePageService;

    @GetMapping("/inputDate")
    public JSONResult inputDate(PaymentProcessDTO paymentProcessDTO){

        return new JSONResult(appHomePageService.inputDate(paymentProcessDTO));
    }
}
