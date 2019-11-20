package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import com.cqut.czb.bn.service.partnerAndOperateCenter.GeneralPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 作者：  WangYa
 * 时间：  2019/11/17 9:36
 * 描述：
 */

@RestController
@RequestMapping("/api/GeneralPartner")
public class GeneralPartnerManagementController {

    @Autowired
    GeneralPartnerService service;

    @Autowired
    CareerStatisticsService statisticsService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 普通合伙人表格获取
     * @param
     * @return
     */
    @GetMapping("/getGeneralPartnerList")
    public JSONResult getGeneralPartnerList(GeneralPartnerUserPageDTO pageDTO){
//        User user = (User)redisUtils.get(principal.getName());
//        if(user==null){
//            return new JSONResult("没有权限", 500);
//        }
//        if(user.getUserId() == null){
//            return new JSONResult("没有权限", 500);
//        }
        return service.getGeneralPartnerList(pageDTO);
    }

    /**
     * 普通合伙人详情收益获取
     * @param
     * @return
     */
    @GetMapping("/getReturn")
    public JSONResult getReturn(Integer condition, String userId){
        return statisticsService.getDirectAndIndirectIncome(condition,userId);
    }

    /**
     * 普通合伙人详情发展人数获取
     * @param
     * @return
     */
    @GetMapping("/getNumberOfDevelopment")
    public JSONResult getNumberOfDevelopment(String userId, Integer condition){

        return service.getNumberOfDevelopment(userId,condition);
    }
}
