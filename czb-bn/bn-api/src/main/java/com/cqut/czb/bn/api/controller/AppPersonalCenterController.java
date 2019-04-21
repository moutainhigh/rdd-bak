package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 创建人：陈德强
 * 作用：手机app个人中业务处理
 * 创建时间：2019/4/21
 */

@RestController
@RequestMapping("/AppPersonalCenter")
public class AppPersonalCenterController {

    @Autowired
    AppPersonalCenterController appPersonalCenterController;

    @Autowired
    AppPersonalCenterService appPersonalCenterService;

    /**
     * app通过userId获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/selectUser",method = RequestMethod.GET)
    public JSONResult selectAnnouncement(@Validated String userId){
        return new JSONResult(appPersonalCenterService.selectUser(userId));
    }

    /**
     * app通过useId获取用户收益信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/selectWallet",method = RequestMethod.GET)
    public JSONResult selectWallet(@Validated String userId){
        return new JSONResult<List<UserIncomeInfo>>(appPersonalCenterService.selectUserIncomeInfo(userId));
    }

}
