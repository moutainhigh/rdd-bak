package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralLog;

public interface IntegralLogMapper {
    int deleteByPrimaryKey(String integralLogId);

    int insert(IntegralLog record);

    int insertSelective(IntegralLog record);

    IntegralLog selectByPrimaryKey(String integralLogId);

    int updateByPrimaryKeySelective(IntegralLog record);

    int updateByPrimaryKey(IntegralLog record);
}
