package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.CareerStatisticsMapperExtra;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.CareerStatisticsDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareerStatisticsServiceImpl implements CareerStatisticsService {
    @Autowired
    CareerStatisticsMapperExtra mapperExtra;

    @Override
    public JSONResult statistics(String userId) {
        //统计收益信息
        CareerStatisticsDTO firstData = mapperExtra.statistics(userId);
        //统计人数信息
        CareerStatisticsDTO secondData = mapperExtra.getUsers(userId);

        //因为返回结果frstData，所以将secondData数据设置到firstData中
        if( null != secondData.getPartnerCount())
            firstData.setPartnerCount(secondData.getPartnerCount());
        else
            firstData.setPartnerCount(0);

        if( null != secondData.getOrdinaryUserCount())
            firstData.setOrdinaryUserCount(secondData.getOrdinaryUserCount());
        else
            firstData.setOrdinaryUserCount(0);

        if( null != secondData.getDirectVipCount())
            firstData.setDirectVipCount(secondData.getDirectVipCount());
        else
            firstData.setDirectVipCount(0);

        if( null != secondData.getIndirectVipCount())
            firstData.setIndirectVipCount(secondData.getIndirectVipCount());
        else
            firstData.setIndirectVipCount(0);

        return new JSONResult("统计数据查询成功", 200, firstData);
    }
}
