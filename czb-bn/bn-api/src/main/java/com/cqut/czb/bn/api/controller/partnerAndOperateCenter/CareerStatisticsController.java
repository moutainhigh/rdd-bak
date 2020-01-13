package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectInputDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryUserDirectInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import com.cqut.czb.bn.service.partnerAndOperateCenter.OrdinaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/career")
public class CareerStatisticsController {

    @Autowired
    CareerStatisticsService service;

    @Autowired
    OrdinaryStatisticsService statisticsService;

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

    /**
     * 合伙人直推间推收益
     * @param principal
     * @param inputDTO
     * @return
     */
    @GetMapping("/getDirectAndIndirectIncome")
    public JSONResult getDirectAndIndirectIncome(Principal principal, DirectAndIndirectInputDTO inputDTO) {
        User user = (User)redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("没有权限", 500);
        }

        return  service.getDirectAndIndirectIncome(inputDTO.getType(), user.getUserId());
    }

    /**
     * 普通用户直推人数
     * @param inputDTO
     * @return
     */
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
    public JSONResult getNumberOfDevelopment(Principal principal, StatisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        User user =  (User)redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("没有权限", 500);
        }
        statisticsDevelopmentNumbers.setUserId(user.getUserId());
        return service.getNumberOfDevelopment(statisticsDevelopmentNumbers);
    }

    /**
     * 事业合伙人中心-普通合伙人管理详情-发展人数获取
     * @param
     * @return
     */
    @GetMapping("/getDevelopment")
    public JSONResult getDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        return statisticsService.getNumberOfDevelopment(statisticsDevelopmentNumbers);
    }

    /**
     * 事业合伙人中心-普通合伙人管理详情-直推间推收益
     * @param statisticsDevelopmentNumbers
     * @return
     */
    @GetMapping("/getIncome")
    public JSONResult getIncome(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        return  service.getDirectAndIndirectIncome(statisticsDevelopmentNumbers.getCondition(), statisticsDevelopmentNumbers.getUserId());
    }

    @GetMapping("/initPermission")
    public JSONResult initPermission(){
        return service.initPermission();
    }
}
