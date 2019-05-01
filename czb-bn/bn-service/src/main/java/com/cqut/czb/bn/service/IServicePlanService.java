package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.servicePlan.ServicePlanInputDTO;
import com.cqut.czb.bn.entity.entity.ServicePlan;
import com.github.pagehelper.PageInfo;

public interface IServicePlanService {

    boolean insertServicePlan(ServicePlanInputDTO servicePlanInputDTO);

    PageInfo<ServicePlan> selectServicePlan(PageDTO pageDTO);
}
