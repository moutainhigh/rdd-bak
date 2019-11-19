package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryUserDirectInputDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.UserIncomeStatisticDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import com.cqut.czb.bn.service.partnerAndOperateCenter.GeneralPartnerUserService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @ClassName: GeneralPartnerUserController
 * @Author: Iriya720
 * @Date: 2019/11/16
 * @Description: 普通用户管理
 * @version: v1.0
 */
@RestController
@RequestMapping("/api/general")
public class GeneralPartnerUserController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private GeneralPartnerUserService service;

    @Autowired
    CareerStatisticsService careerService;

    /**
     * 获取普通合伙人下普通用户数据
     * @param
     * @param pageDTO
     * @return
     */
    @GetMapping("/getTableData")
    public JSONResult getTableData(Principal principal,GeneralPartnerUserPageDTO pageDTO){
        User user = (User)redisUtils.get(principal.getName());
        return service.getGeneralPartnerUserTableData(user,pageDTO);
    }

    /**
     * 获取普通合伙人直推收益
     * @param userIncomeStatisticDTO
     * @return
     */
    @GetMapping("/incomeStatistic")
    public JSONResult incomeStatistic(UserIncomeStatisticDTO userIncomeStatisticDTO){
        return service.getIncomeStatistic(userIncomeStatisticDTO);
    }

    /**
     * 普通合伙人推荐人数统计
     * @param
     * @return
     */
    @GetMapping("/promoterStatistic")
    public JSONResult promoterStatistic(OrdinaryUserDirectInputDTO inputDTO){
        return careerService.getOrdinaryDirectNum(inputDTO.getType(), inputDTO.getUserId());
    }
}
