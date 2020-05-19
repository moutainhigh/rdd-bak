package com.cqut.czb.bn.api.controller.autoRecharge;


import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cqut.czb.bn.entity.entity.User;
import java.security.Principal;

@RestController
@RequestMapping("/api/UserRechargeController")
public class UserRechargeController {
    @Autowired
    UserRechargeService userRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取余额
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    public JSONResult getBalance(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserId());
        return new JSONResult("余额获取成功",200, userRechargeService.getBalance(user.getUserId()));
    }

    /**
     * 获取详情
     * @param principal
     * @param pageDTO
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/getRechargeDetails", method = RequestMethod.POST)
    public JSONResult getRechargeDetails(Principal principal, UserRechargeDTO pageDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null){
            return null;
        }
        return new JSONResult(userRechargeService.getRechargeDetails(user.getUserId(),pageDTO));

    }

    /**
     * 获取总充值金额
     * @param principal
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/getTotalRechargeAmount",method = RequestMethod.GET)
    public JSONResult getTotalRechargeAmount(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return new JSONResult(userRechargeService.getTotalRechargeAmount(user));
    }

    /**
     * 插入充值记录
     * @param principal
     * @param userRechargeDTO
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/insertBatchRecharge",method = RequestMethod.POST)
    public JSONResult insertBatchRecharge(Principal principal,UserRechargeDTO userRechargeDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return new JSONResult(userRechargeService.insertBatchRecharge(user,userRechargeDTO));
    }



    /**
     * 获取账号
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/getAccount", method = RequestMethod.GET)
    public JSONResult getAccount(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserId());
        return new JSONResult("账号获取成功",200, user.getUserAccount());
    }
}
