package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralDetailsDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralLog;

import java.util.List;

public interface IntegrallogMapperExtra {
    /**
     * 获取积分详情
     * @param userId
     * @return
     */
    List<IntegralLogDTO> getIntegralDetailsList(String userId);

    List<IntegralDetailsDTO> getOfferIntegralDetail(String userId);

    int insertBatch(List<IntegralLog> integralLogList);
}
