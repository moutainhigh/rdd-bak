package com.cqut.czb.bn.api.controller.electricityRecharge;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityConsumption;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/ElectricityConsumptionController")
public class ElectricityConsumptionController {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ElectricityConsumption electricityConsumption;

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
        System.out.println("余额查询:"+user.getUserId());
        return new JSONResult("余额获取成功",200,electricityConsumption.getBalance(user.getUserId()));
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
        return new JSONResult(electricityConsumption.getTotalRechargeAmount(user));
    }

    /**
     * 插入充值记录
     * @param principal
     * @param userRechargeDTO
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/insertElectricityRecharge",method = RequestMethod.POST)
    public JSONResult insertBatchRecharge(Principal principal, UserRechargeDTO userRechargeDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return new JSONResult(electricityConsumption.insertElectricityRecharge(user,userRechargeDTO));
    }

    //表单获取
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "线下大客户")
    @RequestMapping(value = "/getCustomers",method = RequestMethod.POST)
    public JSONResult getCustomers(Principal principal,ElectricityRechargeDto electricityRechargeDto){
        System.out.println(electricityRechargeDto);
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return electricityConsumption.getCustomers(user,electricityRechargeDto);
    }
}
