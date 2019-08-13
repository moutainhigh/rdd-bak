package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;

public interface RiderEvaluateMapper {
    int deleteByPrimaryKey(String evaluateId);

    int insert(RiderEvaluate record);

    int insertSelective(RiderEvaluate record);

    RiderEvaluate selectByPrimaryKey(String evaluateId);

    int updateByPrimaryKeySelective(RiderEvaluate record);

    int updateByPrimaryKey(RiderEvaluate record);
}