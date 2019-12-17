package com.cqut.czb.bn.api.controller.IssueCoupons;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.IssueCoupons.IssueCouponsDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IssueCouponsService.IssueCouponsService;
import com.cqut.czb.bn.util.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/issueCoupons")
public class IssueCouponsController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    IssueCouponsService issueCouponsService;

    @RequestMapping(value = "/getInputInfo",method = RequestMethod.GET)
    public JSONResult getInputInfo(){
        IssueCouponsDTO  k=new IssueCouponsDTO();
        return new JSONResult(issueCouponsService.selectCoupons(k));
    }


}
