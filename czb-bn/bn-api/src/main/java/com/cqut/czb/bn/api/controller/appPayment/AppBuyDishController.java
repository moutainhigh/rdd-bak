package com.cqut.czb.bn.api.controller.appPayment;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.CleanServerVehicleDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.InputDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.ParseJSON;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appPaymentService.AppBuyCarWashService;
import com.cqut.czb.bn.service.appPaymentService.AppBuyDishService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/AppBuyDish")
public class AppBuyDishController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppBuyDishService appBuyDishService;

    @RequestMapping(value = "/AliBuyDish" ,method = RequestMethod.POST)
    public JSONResult AliBuyDish(Principal principal,@RequestBody InputDishDTO inputDishDTO){
//        String dishInfo = "[{'dishId':'2','num':'2'},{'dishId':'2','num':'7'},{'dishId':'3','num':'7'}]";
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("454654555");
//        inputDishDTO.setDishInfo(dishInfo);
        String info =appBuyDishService.AliBuyDish(user,inputDishDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }

    @RequestMapping(value = "/WeChatBuyDish" ,method = RequestMethod.POST)
    public JSONResult WeChatBuyDish(Principal principal,@RequestBody InputDishDTO inputDishDTO){
        User user = (User)redisUtils.get(principal.getName());
        JSONObject info =appBuyDishService.WeChatBuyDish(user,inputDishDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }
}
