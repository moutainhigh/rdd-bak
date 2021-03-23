package com.cqut.czb.bn.api.controller.wCardAndPICCode;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.wCardAndPICCode.WCardAndPICCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/WCardAndPICCode")
public class WCardAndPICCodeController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    WCardAndPICCodeService wCardAndPICCodeService;

    /**
     * 获取用户所有小程序订单
     * @param principal
     * @return
     */
    @GetMapping("/getCommodityOrder")
    public JSONResult getCommodityOrder(Principal principal, Integer type){
        String userId = ((User)redisUtils.get(principal.getName())).getUserId();
        return new JSONResult(wCardAndPICCodeService.getCommodityOrder(userId, type));
    }


}
