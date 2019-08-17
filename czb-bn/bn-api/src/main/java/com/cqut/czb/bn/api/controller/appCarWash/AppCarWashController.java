package com.cqut.czb.bn.api.controller.appCarWash;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppCarWashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/CarWash")
public class AppCarWashController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppCarWashService appCarWashService;

    /**
     * 获取信息录入已有信息
     * @return
     */
    @RequestMapping(value = "/getInputInfo",method = RequestMethod.GET)
    public JSONResult getInputInfo(Principal principal){
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(appCarWashService.selectCleanServerVehicle(user.getUserId()));
    }

    /**
     * 获取单条服务信息
     * @return
     */
    @RequestMapping(value = "/getOneServiceInfo",method = RequestMethod.GET)
    public JSONResult getOneServiceInfo(@RequestParam("serverId") String serverId){
        return new JSONResult(appCarWashService.selectOneService(serverId));
    }

    /**
     * 获取服务商品信息
     */
    @RequestMapping(value = "/getService",method = RequestMethod.GET)
    public JSONResult getService(){
        return  new  JSONResult(appCarWashService.SelectService());
    }

    /**
     * 获取用户须知
     */
    @RequestMapping(value = "/getUserInstruction",method = RequestMethod.GET)
    public JSONResult getUserInstruction(){
        return new JSONResult(appCarWashService.getUserInstruction());
    }

    /**
     * 获取优惠劵
     */
    @RequestMapping(value = "/getCoupons",method = RequestMethod.GET)
    public JSONResult getCoupons(Principal principal,@RequestParam("couponId") String couponId)
    {
//        User user = (User) redisUtils.get(principal.getName());
//        if(user==null&&couponId==null){
//            return null;
//        }
        User user=new User();
        user.setUserId("15310490895");
        return new JSONResult(appCarWashService.getCoupons(user.getUserId(),couponId));
    }

}
