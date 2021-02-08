package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralIogDTO;

import java.util.List;

public interface IntegrallogMapperExtra {
    /**
     * 获取积分详情
     * @param userId
     * @return
     */
    List<IntegralIogDTO> list(String userId);
}
