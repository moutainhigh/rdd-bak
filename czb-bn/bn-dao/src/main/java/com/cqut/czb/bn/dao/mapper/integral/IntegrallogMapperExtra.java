package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;

import java.util.List;

public interface IntegrallogMapperExtra {
    /**
     * 获取积分详情
     * @param userId
     * @return
     */
    List<IntegralLogDTO> getIntegralDetailsList(String userId);
}
