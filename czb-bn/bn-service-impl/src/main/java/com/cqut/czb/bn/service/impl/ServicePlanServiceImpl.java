package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ServicePlanMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.servicePlan.ServicePlanInputDTO;
import com.cqut.czb.bn.entity.entity.ServicePlan;
import com.cqut.czb.bn.service.IServicePlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePlanServiceImpl implements IServicePlanService {

    @Autowired
    ServicePlanMapperExtra servicePlanMapperExtra;

    @Override
    public boolean insertServicePlan(ServicePlanInputDTO servicePlanInputDTO) {
        return servicePlanMapperExtra.insertServicePlan(servicePlanInputDTO) > 0;
    }

    @Override
    public PageInfo<ServicePlan> selectServicePlan(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ServicePlan> servicePlanList = servicePlanMapperExtra.selectServicePlan();
        return new PageInfo<>(servicePlanList);
    }
}
