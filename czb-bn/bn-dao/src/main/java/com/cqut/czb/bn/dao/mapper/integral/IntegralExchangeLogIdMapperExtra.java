package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeLogIdDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchangeLogId;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 袁菘壑
 */
public interface IntegralExchangeLogIdMapperExtra {
    /**
     * @param integralExchangeLogId userId integralExchangeId
     * @return
     */
    IntegralExchangeLogId selectByIntegralExchange(IntegralExchangeLogId integralExchangeLogId);

    List<IntegralExchangeLogIdDTO> getExchangeLogDetails(String userAccount);
}
