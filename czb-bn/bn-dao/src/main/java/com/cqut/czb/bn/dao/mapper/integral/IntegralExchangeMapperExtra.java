package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;

public interface IntegralExchangeMapperExtra {
    IntegralExchange selectByExchangeCode(String exchangeCode);

    int updateByPrimaryKeySync(IntegralExchange record);
}
