package com.cqut.czb.bn.service.impl.directChargingSystem;


import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OilCardRechargeServiceImpl implements OilCardRechargeService {
    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Override
    public List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type) {
        return oilCardRechargeMapperExtra.getOrderInfoList(userId, type);
    }
}
