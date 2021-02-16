package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralInfo;

import java.util.List;

public interface IntegralInfoMapperExtra {
    IntegralInfo selectByUserId(String userId);

    List<IntegralInfoDTO> selectIntegralInfoList(String userAccount);

    int updateByPrimaryKeySync(IntegralInfo record);

    int getUserAmount();
}
