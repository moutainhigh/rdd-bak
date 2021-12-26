package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.github.pagehelper.PageInfo;

public interface DirectChargingCommodityService {
    PageInfo getAllCommodity(PageDTO pageDTO);

    PageInfo getElectricityCommodity(PageDTO pageDTO);
}
