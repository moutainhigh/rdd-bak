package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * 创建人：陈德强
 * 作用：手机app个人中业务处理
 * 创建时间：2019/4/21
 */

@RestController
@RequestMapping("/api/AppPersonalCenter")
public class AppPersonalCenterController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppPersonalCenterController appPersonalCenterController;

    @Autowired
    AppPersonalCenterService appPersonalCenterService;

    /**
     * app通过useId获取用户收益信息
     * @param principal
     * @return
     */
    @RequestMapping(value = "/selectWallet",method = RequestMethod.GET)
    public JSONResult selectWallet(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult<List<UserIncomeInfoDTO>>(appPersonalCenterService.selectUserIncomeInfo(user.getUserId()));
    }

    @RequestMapping(value = "/getShareInfo",method = RequestMethod.GET)
    public JSONResult getShareInfo(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(user.getUserAccount());
    }

    @RequestMapping(value= "/getMyGTPetrolList",method = RequestMethod.GET)
    public JSONResult getMyGTPetrolList(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
     return new JSONResult(appPersonalCenterService.getGTSoldPetrolForUser(user.getUserId()));

    }

    @RequestMapping(value= "/getMyPhysicalPetrolList",method = RequestMethod.GET)
    public JSONResult getMyPetrolList(Principal principal,String petrolKind){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appPersonalCenterService.getPhysicalCardRechargeRecords(user.getUserId(),petrolKind));
    }

    /**
     *  * 获取菜单信息
     * @return
     */
    @RequestMapping(value= "/getPersonalCenterMenu",method = RequestMethod.GET)
    public JSONResult getPersonalCenterMenu(@Validated @RequestBody AppRouter appRouter){

        return new JSONResult(false);
    }

}
