package com.cqut.czb.bn.api.controller.appCarWash;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppCarWashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 信息录入
     */
    @RequestMapping(value = "/inputInServiceInfo",method = RequestMethod.GET)
    public JSONResult inputServiceInfo(){
        return null;
    }

    /**
     * 获取信息录入已有信息
     * @return
     */
    @RequestMapping(value = "/getInputInfo",method = RequestMethod.GET)
    public JSONResult getInputInfo(){
        return null;
    }

    /**
     * 获取服务商品信息
     */
    @RequestMapping(value = "/getService",method = RequestMethod.GET)
    public JSONResult getService(){
        return  new  JSONResult(appCarWashService.SelectService());
    }

}
