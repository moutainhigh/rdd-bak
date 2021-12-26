package com.cqut.czb.bn.api.controller.directCustomerManager;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomerManager.DirectAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/DirectAgentController")
public class DirectAgentController {

    @Autowired
    DirectAgentService directAgentService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取余额
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "直充代理人")
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    public JSONResult getBalance(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserId());
        return new JSONResult("余额获取成功",200, directAgentService.getBalance(user.getUserId()));
    }

    /**
     * 获取账号
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "直充代理人")
    @RequestMapping(value = "/getAccount", method = RequestMethod.GET)
    public JSONResult getAccount(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserId());
        return new JSONResult("账号获取成功",200, user.getUserAccount());
    }

    /**
     * 插入充值记录
     * @param principal
     * @param userRechargeDTO
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "直充代理人")
    @RequestMapping(value = "/insertRecharge",method = RequestMethod.POST)
    public JSONResult insertBatchRecharge(Principal principal, UserRechargeDTO userRechargeDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return new JSONResult(directAgentService.insertRecharge(user,userRechargeDTO));
    }

    /**
     * 获取详情
     * @param principal
     * @param pageDTO
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "直充代理人")
    @RequestMapping(value = "/getRechargeDetailsList", method = RequestMethod.POST)
    public JSONResult getRechargeDetails(Principal principal, DirectCustomerManagerDto pageDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null){
            return null;
        }
        return new JSONResult(directAgentService.getRechargeDetails(user.getUserId(),pageDTO));
    }

    /**
     * 获取充值总额
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "直充代理人")
    @RequestMapping(value = "/getTotalRechargeAmount", method = RequestMethod.POST)
    public JSONResult getTotalRechargeAmount(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserId());
        return new JSONResult("余额获取成功",200, directAgentService.getTotalRechargeAmount(user.getUserId()));
    }
}
