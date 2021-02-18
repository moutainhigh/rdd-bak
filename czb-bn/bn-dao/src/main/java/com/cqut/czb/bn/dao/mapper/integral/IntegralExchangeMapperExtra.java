package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralDistributionDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;

import java.util.List;

/**
 * 袁菘壑、侯家领
 */
public interface IntegralExchangeMapperExtra {
    IntegralExchange selectByIntegralExchange(IntegralExchangeDTO integralExchange);

    int updateByPrimaryKeySync(IntegralExchange record);

    List<IntegralExchange> getExchangeList(String exchangeSourceId);

    int insert(IntegralExchangeDTO record);

    List<IntegralDistributionDTO> getIntegralDistributionDetails(IntegralDistributionDTO integralDistributionDTO);
}
