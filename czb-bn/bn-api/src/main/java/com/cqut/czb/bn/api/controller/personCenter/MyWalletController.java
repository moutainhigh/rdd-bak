package com.cqut.czb.bn.api.controller.personCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 作者：谭深化
 * 模块：app个人中心，我的钱包
 * 业务：提现
 */
@RestController
@RequestMapping("/personCenter")
public class MyWalletController {

    @Autowired
    private MyWallet myWallet;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/myWallet/withDraw", method = RequestMethod.POST)
    public JSONResult withDraw(Principal principal, @RequestBody AlipayRecordDTO alipayRecordDTO){

        if (principal == null || principal.getName() == null)
            return new JSONResult("没有权限", 400, "没有权限");
        User user = (User)redisUtils.get(principal.getName());
        // null
        if(user == null || user.getUserId()==null){
            return new JSONResult("没有权限", 400, "没有权限");
        }
        return myWallet.withDraw(alipayRecordDTO, user.getUserId());
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    public JSONResult getBalance(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(myWallet.getBalance(user.getUserId()));
    }

    @RequestMapping(value = "/getWithdrawLog", method = RequestMethod.GET)
    public JSONResult getWithdrawLog(Principal principal){
        return new JSONResult(myWallet.getWithdrawLog("155680497010838"));
    }
}
