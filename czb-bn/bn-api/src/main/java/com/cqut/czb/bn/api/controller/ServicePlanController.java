package com.cqut.czb.bn.api.controller;


import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.servicePlan.ServicePlanInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IServicePlanService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ServicePlanController 服务套餐接口
 * 设计者:   曹渝
 * 更新日期: 2019/05/01
 */
public class ServicePlanController {

    @Autowired
    IServicePlanService servicePlanService;

    @RequestMapping(value = "/insertServicePlan",method = RequestMethod.POST)
    public JSONResult insertServicePlan(@Validated @RequestBody ServicePlanInputDTO servicePlanInputDTO){
        boolean isInsert = servicePlanService.insertServicePlan(servicePlanInputDTO);
        if(isInsert) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "新增成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "新增失败");
        }
    }

    @RequestMapping(value = "/selectServicePlan",method = RequestMethod.GET)
    public JSONResult ServicePlan(PageDTO pageDTO){

        return new JSONResult(servicePlanService.selectServicePlan(pageDTO));
    }
}
