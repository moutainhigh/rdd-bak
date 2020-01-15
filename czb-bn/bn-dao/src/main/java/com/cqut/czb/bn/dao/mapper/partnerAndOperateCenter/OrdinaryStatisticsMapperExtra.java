package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryStatisticsDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import org.apache.ibatis.annotations.Param;

public interface OrdinaryStatisticsMapperExtra {
    OrdinaryStatisticsDTO statistics(@Param("userId") String userId);

    OrdinaryStatisticsDTO getUsers(@Param("userId") String userId);

    OrdinaryStatisticsDTO getNumberOfDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers);
}
