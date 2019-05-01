package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.servicePlan.ServicePlanInputDTO;
import com.cqut.czb.bn.entity.entity.ServicePlan;

import java.util.List;

public interface ServicePlanMapperExtra {

    List<ServicePlan> selectServicePlan();

    int insertServicePlan(ServicePlanInputDTO servicePlanInputDTO);

}