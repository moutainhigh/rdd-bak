package com.cqut.czb.bn.api.controller.personCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.WeiXinRecordDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * 作者：谭深化
 * 模块：app个人中心，我的钱包
 * 业务：提现
 */
@RestController
@RequestMapping("/api/personCenter")
public class MyWalletController {

    @Autowired
    private MyWallet myWallet;

    @Autowired
    RedisUtils redisUtils;

//    @RequestMapping(value = "/myWallet/withDraw", method = RequestMethod.POST)
//    public JSONResult withDraw(Principal principal, @RequestBody AlipayRecordDTO alipayRecordDTO) {
//
//        if (principal == null || principal.getName() == null)
//            return new JSONResult("没有权限", 400, "没有权限");
//        User user = (User) redisUtils.get(principal.getName());
//        // null
//        if (user == null || user.getUserId() == null) {
//            return new JSONResult("没有权限", 400, "没有权限");
//        }
//        return myWallet.withDraw(alipayRecordDTO, user.getUserId());
//    }
//
//    @RequestMapping(value = "/myWallet/wspWithDrawToWeChat", method = RequestMethod.POST)
//    public JSONResult wspWithDrawToWeChat(BigDecimal paymentAmount, Principal principal) {
//        User user = (User) redisUtils.get(principal.getName());
//        if (user == null || user.getUserId() == null) {
//            return null;
//        }
//        return myWallet.wspWithDrawToWeChat(paymentAmount, user);
//    }
//
//    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
//    public JSONResult getBalance(Principal principal) {
//        User user = (User) redisUtils.get(principal.getName());
//        return new JSONResult(myWallet.getBalance(user.getUserId()));
//    }
//
//    @RequestMapping(value = "/getWithdrawLog", method = RequestMethod.GET)
//    public JSONResult getWithdrawLog(Principal principal) {
//        User user = (User) redisUtils.get(principal.getName());
//        return new JSONResult(myWallet.getWithdrawLog(user.getUserId()));
//    }

    @RequestMapping(value = "/myWallet/weiXinWithdrawal", method = RequestMethod.POST)
    public JSONResult withDraw(Principal principal, @RequestBody WeiXinRecordDTO weiXinRecordDTO) {
        System.out.println("微信提现后台确认");
        if (principal.getName() == null) {
            return new JSONResult("没有权限", 400, "没有权限");
        }
        User user = (User) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return new JSONResult("没有权限", 400, "没有权限");
        }
        JSONResult info = myWallet.wxWithDraw(user,weiXinRecordDTO);
        return info;
    }

    @RequestMapping(value = "/myWallet/wxPostDraw", method = RequestMethod.POST)
    public JSONResult wxDraw(Principal principal, @RequestBody WeiXinRecordDTO weiXinRecordDTO) {
        System.out.println("微信提现后台生成订单");
        User user = (User) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return new JSONResult("没有权限", 400, "没有权限");
        }
        return myWallet.wxPostDraw(user,weiXinRecordDTO);
    }
}
