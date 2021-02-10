package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;

import java.util.List;

/**
 * 袁菘壑
 */
public interface IntegralExchangeMapperExtra {
    IntegralExchange selectByExchangeCode(String exchangeCode);

    int updateByPrimaryKeySync(IntegralExchange record);

    List<IntegralExchange> getExchangeList(String exchangeSourceId);
}
