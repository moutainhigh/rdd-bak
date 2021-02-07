package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;

public interface IntegralExchangeMapper {
    int deleteByPrimaryKey(String integralExchange);

    int insert(IntegralExchange record);

    int insertSelective(IntegralExchange record);

    IntegralExchange selectByPrimaryKey(String integralExchange);

    int updateByPrimaryKeySelective(IntegralExchange record);

    int updateByPrimaryKey(IntegralExchange record);
}
