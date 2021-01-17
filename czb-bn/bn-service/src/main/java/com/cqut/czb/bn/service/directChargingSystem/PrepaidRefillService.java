package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PrepaidRefillService {
    List<DirectChargingCommodity> getGoodsPrice(Integer type);
}
