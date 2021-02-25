package com.cqut.czb.bn.api.controller.equityPayment;

import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */

@RestController
@RequestMapping("/api/equityPayment")
public class EquityPaymentController {
    @RequestMapping(value = "/getCommodityView", method = RequestMethod.GET)
    public JSONResult getCommodityView() {
        return null;
    }
}
