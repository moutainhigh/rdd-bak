package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.statisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface CareerStatisticsService {
    JSONResult statistics(String userId);

    JSONResult getDirectAndIndirectIncome(int type, String userId);

    JSONResult getOrdinaryDirectNum(int type,String userId);

    JSONResult getNumberOfDevelopment(statisticsDevelopmentNumbers statisticsDevelopmentNumbers);

    JSONResult initPermission();
}
