package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.OrdinaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

/**
* 作者:  肖阳
* 时间:  2019/11/17 9:37
* 描述:  普通合伙人统计
*/

@RestController
@RequestMapping("/api/ordinary")

public class OrdinaryStatisticsController {

    @Autowired
    OrdinaryStatisticsService service;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 普通合伙人中心统计数据-截至目前发展及收益
     * @param
     * @return
     */
    @GetMapping("/statistics")
    public JSONResult statistics(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
//        User user = new User();
//        user.setUserId("54598729134309428");
        if(user.getUserId() == null){
            return new JSONResult("没有权限", 500);
        }

        return service.statistics(user.getUserId());
    }

    /**
     * 普通合伙人中心统计数据-发展人数获取
     * @param
     * @return
     */
    @GetMapping("/getNumberOfDevelopment")
    public JSONResult getNumberOfDevelopment(Principal principal, StatisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        User user =  (User)redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("没有权限", 500);
        }
//        user.setUserId("756152457954521512");
        statisticsDevelopmentNumbers.setUserId(user.getUserId());
//        statisticsDevelopmentNumbers.setCondition(1);
        return service.getNumberOfDevelopment(statisticsDevelopmentNumbers);
    }




}
