package com.cqut.czb.bn.api.controller.personCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.UserInfoService;
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
@RequestMapping("/api/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RedisUtils redisUtils;


    @RequestMapping(value = "/getWithdrawLog", method = RequestMethod.GET)
    public String getWithdrawLog(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return userInfoService.getUserAccount(user.getUserId());
    }
}
