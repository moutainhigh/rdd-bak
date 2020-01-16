package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.ManagerStaticsDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import org.apache.ibatis.annotations.Param;

public interface ManagerStaticsMapperExtra {

/**    获取统计数据 */
    ManagerStaticsDTO statistics();

    Double getAllVipDirectIncome();

/**    获取返利  */
    DirectAndIndirectDTO getDirectAndIndirectIncome(@Param("startTime") String startTime, @Param("endTime")String endTime);

/** 获取直推与间推返利 */
    ManagerStaticsDTO getIncome();

/**    获取发展人数 */
    ManagerStaticsDTO getUsers();

/**    获取图表中发展人数 */
    ManagerStaticsDTO getNumberOfDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers);
}
