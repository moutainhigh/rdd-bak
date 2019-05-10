package com.cqut.czb.bn.api.controller.verifyAsynNoticeInfo;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Iterator;
import java.util.Map;

/**
 * author:陈德强
 * 作用：取消支付后回调接口
 */
@RestController
@RequestMapping("/CancelPay")
public class CancelPayController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/CancelPayPetrol",method = RequestMethod.POST)
    public JSONResult CancelPayPetrol(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user==null||user.getUserId()==null){
            return new JSONResult(ResponseCodeConstants.FAILURE, "用户信息有误");
        }
        Iterator<Map.Entry<String,Petrol>> currentPetrol = PetrolCache.currentPetrolMap.entrySet().iterator();
        System.out.println(PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());
        while(currentPetrol.hasNext()){
            Map.Entry<String, Petrol> entry=currentPetrol.next();
            String key=entry.getKey();
            Petrol petrol=PetrolCache.currentPetrolMap.get(key);
            if(petrol.getOwnerId()==user.getUserId()){
                currentPetrol.remove();        //OK
                petrol.setOwnerId("");//将用户id置为空
                PetrolCache.putBackPetrol("AllpetrolMap",petrol);
                return new JSONResult(ResponseCodeConstants.FAILURE, "订单已取消");
            }
        }
        System.out.println(PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());
        return  new JSONResult(ResponseCodeConstants.FAILURE, "订单已取消");
    }
}
