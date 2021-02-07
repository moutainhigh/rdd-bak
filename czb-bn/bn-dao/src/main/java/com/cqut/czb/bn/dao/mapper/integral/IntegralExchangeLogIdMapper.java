package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralExchangeLogId;

public interface IntegralExchangeLogIdMapper {
    int deleteByPrimaryKey(String integralExchangeLogId);

    int insert(IntegralExchangeLogId record);

    int insertSelective(IntegralExchangeLogId record);

    IntegralExchangeLogId selectByPrimaryKey(String integralExchangeLogId);

    int updateByPrimaryKeySelective(IntegralExchangeLogId record);

    int updateByPrimaryKey(IntegralExchangeLogId record);
}
