package com.cqut.czb.bn.api.controller.integral;

import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/08
 */
@RestController
@RequestMapping("/api/integral")
public class integralController {


    /**
     * 获取个人积分明细
     * @param
     * @return
     */
    @RequestMapping(value = "/getIntegralDetail",method = RequestMethod.GET)
    public JSONResult getIntegralDetail(@RequestBody Principal principal) {
        return null;
    }

    /**
     * 购买积分
     * @param
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/purchaseIntegral",method = RequestMethod.POST)
    public JSONResult purchaseIntegral(@RequestBody Principal principal) {
        return null;
    }

    /**
     * 兑换积分
     * @param
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/exchangeIntegral",method = RequestMethod.POST)
    public JSONResult exchangeIntegral(@RequestBody Principal principal) {
        return null;
    }

    /**
     * 赠送积分
     * @param
     * @return
     */
    @RequestMapping(value = "/giveIntegral",method = RequestMethod.POST)
    public JSONResult giveIntegral(@RequestBody Principal principal) {
        return null;
    }

    /**
     * 积分折扣
     * @param
     * @return
     */
    public JSONResult deduction() {
        return null;
    }
}
