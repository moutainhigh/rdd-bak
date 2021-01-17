package com.cqut.czb.bn.service.impl.directChargingSystem;


import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OilCardRechargeServiceImpl implements OilCardRechargeService {
    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;
}
