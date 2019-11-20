package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectInputDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryUserDirectInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.statisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public JSONResult statistics(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("没有权限", 500);
        }

        return service.statistics(user.getUserId());
    }

    @GetMapping("/getDirectAndIndirectIncome")
    public JSONResult getDirectAndIndirectIncome(Principal principal, DirectAndIndirectInputDTO inputDTO) {
        User user = (User)redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("没有权限", 500);
        }

        return  service.getDirectAndIndirectIncome(inputDTO.getType(), user.getUserId());
    }

    @GetMapping("/getOrdinaryDirectNum")
    public JSONResult getOrdinaryDirectNum(OrdinaryUserDirectInputDTO inputDTO) {
        return  service.getOrdinaryDirectNum(inputDTO.getType(), inputDTO.getUserId());
    }

    /**
     * 事业合伙人中心统计数据-发展人数获取
     * @param
     * @return
     */
    @GetMapping("/getNumberOfDevelopment")
    public JSONResult getNumberOfDevelopment(Principal principal, statisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
//        User user =  (User)redisUtils.get(principal.getName());
        statisticsDevelopmentNumbers.setUserId("156342470371369");
        statisticsDevelopmentNumbers.setCondition(1);
//        User user = new User();
//        user.setUserId("756152457954521512");
        return service.getNumberOfDevelopment(statisticsDevelopmentNumbers);
    }
}
