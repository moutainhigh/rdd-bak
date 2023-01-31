package com.cqut.czb.bn.api.controller.autoRecharge;


import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.ExchangeCodeQuery;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.RechargeOrder;
import com.cqut.czb.bn.entity.entity.exchangeCode.ExchangeCode;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ExchangeCodeService;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.cqut.czb.bn.entity.entity.User;
import java.security.Principal;

@RestController
@RequestMapping("/api/UserRechargeController")
public class UserRechargeController {
    @Autowired
    UserRechargeService userRechargeService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ExchangeCodeService exchangeCodeService;

    /**
     * 获取余额
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
    @RequestMapping(value = "/insertBatchRecharge",method = RequestMethod.POST)
    public JSONResult insertBatchRecharge(Principal principal,UserRechargeDTO userRechargeDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return new JSONResult(userRechargeService.insertBatchRecharge(user,userRechargeDTO));
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/drawback",method = RequestMethod.POST)
    public JSONResult drawback(String orderId){
        if (userRechargeService.drawbackWithPet(orderId, true)){
            return new JSONResult("成功",200);
        }
        return new JSONResult("失败",500);
    }


    /**
     * 获取账号
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
     * 修改卡号
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
//    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/updatePetrolNum", method = RequestMethod.POST)
    public JSONResult updatePetrolNum(Principal principal,UserRechargeDTO userRechargeDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        System.out.println(user.getUserId());
        if(userRechargeService.updatePetrolNum(userRechargeDTO)){
            return new JSONResult("卡号修改成功",200, true);
        }
        else {
            return new JSONResult("卡号修改失败",500, false);
        }
    }

    @GetMapping("/getUserExCodes")
    public JSONResult getUserExCodes(Principal principal, ExchangeCodeQuery query){
        try {
            User user = (User)redisUtils.get(principal.getName());
            if (user == null) {
                return null;
            }
            query.setUserid(user.getUserId());
            Page<ExchangeCode> res = exchangeCodeService.queryByPage(query, new PageRequest(0, 512));
            return new JSONResult(200, res);
        } catch (Exception e){
            e.printStackTrace();
            return new JSONResult(400,null);
        }
    }

    @GetMapping("/getExCodes")
    @PermissionCheck(role = "管理员")
    public JSONResult getExCodes(ExchangeCodeQuery query){
        try {
            Page<ExchangeCode> res = exchangeCodeService.queryByPage(query, new PageRequest(query.getPage()-1, query.getSize()));
            return new JSONResult(200, res);
        } catch (Exception e){
            e.printStackTrace();
            return new JSONResult(400,null);
        }
    }

    @GetMapping("/genCode")
    @PermissionCheck(role = "管理员")
    public JSONResult genCode(@RequestParam double amount, @RequestParam int num){
        try {
            exchangeCodeService.genCode(num, amount);
            return new JSONResult("生成成功",200);
        } catch (Exception e){
            e.printStackTrace();
            return new JSONResult("生成失败",400);
        }
    }

    @GetMapping("/exchangeCode")
    public JSONResult genCode(Principal principal, @RequestParam String code){
        try {
            User user = (User)redisUtils.get(principal.getName());
            if (user == null) {
                return new JSONResult("登录过期",400);
            }
            return exchangeCodeService.exchangeCode(user, code);
        } catch (Exception e){
            e.printStackTrace();
            return new JSONResult("兑换失败",400);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @RequestMapping(value = "/insertCommonOrder",method = RequestMethod.POST)
    public JSONResult insertCommonOrder(Principal principal, RechargeOrder order){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return userRechargeService.insertCommonOrder(user,order, 10);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @RequestMapping(value = "/insertCommonOrderPhone",method = RequestMethod.POST)
    public JSONResult insertCommonOrderPhone(Principal principal, RechargeOrder order){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return userRechargeService.insertCommonOrder(user,order, 11);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @RequestMapping(value = "/insertCommonOrderElect",method = RequestMethod.POST)
    public JSONResult insertCommonOrderElect(Principal principal, RechargeOrder order){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return userRechargeService.insertCommonOrder(user,order, 12);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @RequestMapping(value = "/getCommonUserOrder", method = RequestMethod.POST)
    public JSONResult getCommonUserOrder(Principal principal, DirectChargingOrderDto pageDTO){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null){
            return null;
        }
        pageDTO.setUserId(user.getUserId());
        return userRechargeService.getCommonUserOrder(pageDTO);
    }
}
