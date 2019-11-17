package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.CareerStatisticsDTO;
import org.apache.ibatis.annotations.Param;

public interface CareerStatisticsMapperExtra {
    CareerStatisticsDTO statistics(@Param("userId") String userId);

    CareerStatisticsDTO getUsers(@Param("userId") String userId);
}
