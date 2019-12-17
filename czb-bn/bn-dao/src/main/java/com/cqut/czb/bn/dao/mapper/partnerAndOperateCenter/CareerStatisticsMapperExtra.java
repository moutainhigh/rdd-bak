package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.CareerStatisticsDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryUserDirectDTO;
import com.cqut.czb.bn.entity.entity.UserRole;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.statisticsDevelopmentNumbers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CareerStatisticsMapperExtra {
    CareerStatisticsDTO statistics(@Param("userId") String userId);

    CareerStatisticsDTO getUsers(@Param("userId") String userId);

    DirectAndIndirectDTO getDirectAndIndirectIncome(@Param("userId")String userId, @Param("startTime") String startTime, @Param("endTime")String endTime);

    OrdinaryUserDirectDTO getOrdinaryDirectNum(@Param("userId")String userId,  @Param("startTime") String startTime, @Param("endTime")String endTime);

    CareerStatisticsDTO getNumberOfDevelopment(statisticsDevelopmentNumbers statisticsDevelopmentNumbers);

    Double getAllVipDirectIncome(@Param("userId")String userId);

    List<UserRole> selectFirstUser(@Param(("partner")) Integer partner);

    int insertPermission(@Param("list") List<UserRole> list);

    int updateLoginPc(@Param("list") List<UserRole> list);
}
