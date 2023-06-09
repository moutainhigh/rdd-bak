package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ServicePlan;

public interface ServicePlanMapper {
    int deleteByPrimaryKey(String planId);

    int insert(ServicePlan record);

    int insertSelective(ServicePlan record);

    ServicePlan selectByPrimaryKey(String planId);

    int updateByPrimaryKeySelective(ServicePlan record);

    int updateByPrimaryKey(ServicePlan record);
}