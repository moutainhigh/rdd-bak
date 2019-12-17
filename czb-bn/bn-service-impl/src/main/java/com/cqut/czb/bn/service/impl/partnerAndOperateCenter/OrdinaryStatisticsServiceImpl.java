package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.OrdinaryStatisticsMapperExtra;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryStatisticsDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.statisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.OrdinaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class OrdinaryStatisticsServiceImpl implements OrdinaryStatisticsService {

    @Autowired
    OrdinaryStatisticsMapperExtra mapperExtra;

    @Override
    public JSONResult statistics(String userId) {
        //统计收益信息
        OrdinaryStatisticsDTO firstData = mapperExtra.statistics(userId);
        //统计人数信息
        OrdinaryStatisticsDTO secondData = mapperExtra.getUsers(userId);

        //因为返回结果frstData，所以将secondData数据设置到firstData中
        if( secondData.getOrdinaryUserCount() != null )
            firstData.setOrdinaryUserCount(secondData.getOrdinaryUserCount());
        else {
            firstData.setOrdinaryUserCount(0);
        }
        firstData.setOrdinaryUser(secondData.getOrdinaryUser());
        if( secondData.getDirectVipCount() != null) {
            firstData.setDirectVipCount(secondData.getDirectVipCount());
        } else {
            firstData.setDirectVipCount(0);
        }

        if( secondData.getIndirectVipCount() != null) {
            firstData.setIndirectVipCount(secondData.getIndirectVipCount());
        } else {
            firstData.setIndirectVipCount(0);
        }
        firstData.setPetrol(firstData.getIndirectPetrolIncome().add(firstData.getDirectPetrolIncome()));
        firstData.setVip(firstData.getIndirectVipIncome().add(firstData.getDirectVipIncome()));

        return new JSONResult("统计数据查询成功", 200, firstData);
    }

    @Override
    public JSONResult getNumberOfDevelopment(statisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        OrdinaryStatisticsDTO numberOfDevelopment = mapperExtra.getNumberOfDevelopment(statisticsDevelopmentNumbers);
        return new JSONResult("发展人数数据查询成功", 200, numberOfDevelopment);
    }
}
