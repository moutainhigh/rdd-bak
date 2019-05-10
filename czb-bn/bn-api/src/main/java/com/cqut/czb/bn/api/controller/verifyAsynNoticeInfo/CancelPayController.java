package com.cqut.czb.bn.api.controller.verifyAsynNoticeInfo;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * author:陈德强
 * 作用：取消支付后回调接口
 */
@RestController
@RequestMapping("/CancelPay")
public class CancelPayController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/CancelPayPetrol",method = RequestMethod.POST)
    public JSONResult CancelPayPetrol(@RequestBody Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        System.out.println(user.getUserId());
        return null;
    }
}
