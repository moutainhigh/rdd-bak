package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface ManagerStaticsService {

    JSONResult statistics();

    JSONResult getNumberOfDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers);

    JSONResult getDirectAndIndirectIncome(int type);

}
