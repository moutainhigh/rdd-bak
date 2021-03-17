package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

public interface DirectChargingPartnerService {
    PageInfo getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto);

    PageInfo getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getTotalRechargeAmount();

    JSONResult getUserTotalRechargeAmount(User user);

    JSONResult deleteDirectChargingPartnerOrder(User user);
}
