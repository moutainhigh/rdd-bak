package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.PrepaidRefillMapperExtra;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;

public class PrepaidRefillServiceImpl implements PrepaidRefillService {
    @Autowired
    PrepaidRefillMapperExtra prepaidRefillMapperExtra;
}
