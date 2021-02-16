package com.cqut.czb.bn.api.controller.paymentNew;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPaymentService;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/H5PaymentBuyCommodity")
public class H5PaymentBuyCommodityController {

    @Autowired
    public RedisUtils redisUtils;
    @Autowired
    H5PaymentBuyCommodityService h5PaymentBuyCommodityService;

    /**
     * 微信商品支付
     * @param   principal,payInputDTO
     * @return
     */
    @RequestMapping(value = "/WeChatAppletPayment", method = RequestMethod.POST)
    public JSONResult WeChatAppletPayment(Principal principal, @RequestBody PayInputDTO payInputDTO) {
        User user = (User)redisUtils.get(principal.getName());
        if(user==null){
            return new JSONResult("未登录",405,null);
        }
        return new JSONResult( h5PaymentBuyCommodityService.WeChatAppletPaymentBuyCommodity(user,payInputDTO));
    }
}
