package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralInfo;

public interface IntegralInfoMapper {
    int deleteByPrimaryKey(String integralInfoId);

    int insert(IntegralInfo record);

    int insertSelective(IntegralInfo record);

    IntegralInfo selectByPrimaryKey(String integralInfoId);

    int updateByPrimaryKeySelective(IntegralInfo record);

    int updateByPrimaryKey(IntegralInfo record);
}
