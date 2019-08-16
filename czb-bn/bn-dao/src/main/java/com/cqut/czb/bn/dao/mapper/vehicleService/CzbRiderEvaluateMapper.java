package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbRiderEvaluate;

public interface CzbRiderEvaluateMapper {
    int deleteByPrimaryKey(String evaluateId);

    int insert(CzbRiderEvaluate record);

    int insertSelective(CzbRiderEvaluate record);

    CzbRiderEvaluate selectByPrimaryKey(String evaluateId);

    int updateByPrimaryKeySelective(CzbRiderEvaluate record);

    int updateByPrimaryKey(CzbRiderEvaluate record);
}