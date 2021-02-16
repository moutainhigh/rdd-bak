package com.cqut.czb.bn.api.controller.paymentNew;

import com.cqut.czb.auth.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/H5PaymentBuyIntegral")
public class H5PaymentBuyIntegralController {

    @Autowired
    RedisUtils redisUtils;



}
