package com.cqut.czb.bn.api.controller.personCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
    public JSONResult withDraw(@RequestBody Principal principal, AlipayRecordDTO alipayRecordDTO){
        User user = (User)redisUtils.get(principal.getName());

        JSONResult json = new JSONResult();
        int code = myWallet.withDraw(alipayRecordDTO, user.getUserId());

        switch (code){
            case 100:
                json.setMessage("账户密码错误");
                json.setCode(code);
                break;
            case 101:
                json.setMessage("提现金额不能是负数");
                json.setCode(code);
                break;
            case 102:
                json.setMessage("提现金额超出余额");
                json.setCode(code);
                break;
            case 103:
                json.setMessage("提现金额不能低于0.1元");
                json.setCode(code);
                break;
            case 1:
                json.setMessage("提现成功");
                json.setCode(code);
                break;
        }
        return json;
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public JSONResult getBalance(@RequestBody Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(myWallet.getBalance(user.getUserId()));
    }
}
