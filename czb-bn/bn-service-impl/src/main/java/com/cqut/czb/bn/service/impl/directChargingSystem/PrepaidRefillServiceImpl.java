package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.PrepaidRefillMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.UserCardRelationDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrepaidRefillServiceImpl implements PrepaidRefillService {
    @Autowired
    PrepaidRefillMapperExtra prepaidRefillMapperExtra;

    @Override
    public List<DirectChargingCommodity> getGoodsPrice(Integer type) {
        return prepaidRefillMapperExtra.getGoodsPrice(type);
    }

    @Override
    public UserCardRelationDto getInfoNum(String userId) {
        return prepaidRefillMapperExtra.getInfoNum(userId);
    }
}
