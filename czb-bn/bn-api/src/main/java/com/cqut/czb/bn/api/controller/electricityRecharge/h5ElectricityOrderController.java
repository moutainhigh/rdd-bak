package com.cqut.czb.bn.api.controller.electricityRecharge;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/electricityRecharge")
public class h5ElectricityOrderController {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ElectricityRechargeService electricityRechargeService;

    /**
     * 获取水电费充值账单
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @RequestMapping(value = "/getElectricityOrder", method = RequestMethod.POST)
    public JSONResult getAccount(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        ElectricityRechargeDto electricityRechargeDto = new ElectricityRechargeDto();
        electricityRechargeDto.setUserId(user.getUserId());
        return new JSONResult("获取成功",200, electricityRechargeService.getCustomers(electricityRechargeDto));
    }
}
