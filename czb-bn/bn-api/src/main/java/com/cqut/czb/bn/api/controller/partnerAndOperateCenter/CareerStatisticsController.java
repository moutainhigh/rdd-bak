package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/career")
public class CareerStatisticsController {

    @Autowired
    CareerStatisticsService service;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 事业合伙人中心统计数据
     * @param
     * @return
     */
    @GetMapping("/statistics")
    public JSONResult statistics() {
//        User user = (User)redisUtils.get(principal.getName());
//        if(user.getUserId() == null){
//            return new JSONResult("没有权限", 500);
//        }
        User user = new User();
        user.setUserId("715727701391196425");
        return service.statistics(user.getUserId());
    }
}
