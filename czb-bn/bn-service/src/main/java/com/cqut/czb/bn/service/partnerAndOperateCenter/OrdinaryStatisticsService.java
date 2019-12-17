package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.statisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;

/**
* 作者:  肖阳
* 时间:  2019/11/17 9:56
* 描述:
*/
public interface OrdinaryStatisticsService {
    JSONResult statistics(String userId);

    JSONResult getNumberOfDevelopment(statisticsDevelopmentNumbers statisticsDevelopmentNumbers);
}
