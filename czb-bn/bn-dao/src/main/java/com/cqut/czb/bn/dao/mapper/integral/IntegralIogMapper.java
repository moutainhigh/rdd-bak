package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralIog;

public interface IntegralIogMapper {
    int deleteByPrimaryKey(String integralLogId);

    int insert(IntegralIog record);

    int insertSelective(IntegralIog record);

    IntegralIog selectByPrimaryKey(String integralLogId);

    int updateByPrimaryKeySelective(IntegralIog record);

    int updateByPrimaryKey(IntegralIog record);
}
