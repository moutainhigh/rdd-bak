package com.cqut.czb.bn.api.controller.paymentNew;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyIntegralService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyIntegralService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/H5PaymentBuyIntegral")
public class H5PaymentBuyIntegralController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    H5PaymentBuyIntegralService h5PaymentBuyIntegralService;

    /**
     * 微信
     */
    @RequestMapping(value = "/WeChatBuyIntegral", method = RequestMethod.POST)
    public JSONResult WeChatRechargeDirect(Principal principal, @RequestBody IntegralRechargeDTO integralRechargeDTO) {
//        User user = (User)redisUtils.get(principal.getName());
        User user = new User();
        JSONObject info =h5PaymentBuyIntegralService.WeChatBuyIntegral(user, integralRechargeDTO);
        if(info==null){
            return new JSONResult("购买失败", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }
    @RequestMapping(value = "AliBuyIntegral",method = RequestMethod.POST)
    public JSONResult AliBuyIntegral(Principal principal, @RequestBody IntegralRechargeDTO integralRechargeDTO){
        User user = (User)redisUtils.get(principal.getName());
        String info =h5PaymentBuyIntegralService.AliBuyIntegral(user,integralRechargeDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }

}
