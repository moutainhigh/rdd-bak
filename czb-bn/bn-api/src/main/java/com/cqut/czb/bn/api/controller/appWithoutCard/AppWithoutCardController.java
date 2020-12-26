package com.cqut.czb.bn.api.controller.appWithoutCard;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.InputRecommendDishDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.appWithoutCard.AppWithoutCardService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/appWithoutCard")
public class AppWithoutCardController {

    @Autowired
    AppWithoutCardService appWithoutCardService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getGoods")
    public JSONResult getGoods(Principal principal, @RequestParam(name="area")String area) {
        User user = (User) redisUtils.get(principal.getName());
        if(area==null)
            return new JSONResult("无法获取当前位置", ResponseCodeConstants.FAILURE);
        return new JSONResult(appWithoutCardService.getGoods(user,area));
    }

    @GetMapping("/getCommodityOrder")
    public JSONResult getCommodityOrder(@Param("isRecharged") Integer isRecharged, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appWithoutCardService.getCommodityOrderList(user.getUserId(),isRecharged));
    }
}
