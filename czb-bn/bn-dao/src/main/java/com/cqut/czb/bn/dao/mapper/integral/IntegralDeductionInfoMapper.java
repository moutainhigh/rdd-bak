package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralDeductionInfo;

public interface IntegralDeductionInfoMapper {
    int deleteByPrimaryKey(String integralDeductionInfoId);

    int insert(IntegralDeductionInfo record);

    int insertSelective(IntegralDeductionInfo record);

    IntegralDeductionInfo selectByPrimaryKey(String integralDeductionInfoId);

    int updateByPrimaryKeySelective(IntegralDeductionInfo record);

    int updateByPrimaryKey(IntegralDeductionInfo record);
}
