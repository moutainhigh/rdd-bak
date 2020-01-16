package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.ManagerStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/manager")
public class ManagerStaticsController {

    @Autowired
    ManagerStaticsService service;

    /**
     * 管理员中心统计数据
     * @param
     * @return
     */
    @GetMapping("/statistics")
    public JSONResult statistics() {
        return service.statistics();
    }

    /**
     * 管理员中心统计数据-发展人数获取
     * @param
     * @return
     */
    @GetMapping("/getNumberOfDevelopment")
    public JSONResult getNumberOfDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        return service.getNumberOfDevelopment(statisticsDevelopmentNumbers);
    }

    /**
     * 代理商合伙人中心-直推间推收益
     * @param
     * @return
     */
    @GetMapping("/getDirectAndIndirectIncome")
    public JSONResult getDirectAndIndirectIncome(DirectAndIndirectInputDTO inputDTO) {
        return  service.getDirectAndIndirectIncome(inputDTO.getType());
    }
}

