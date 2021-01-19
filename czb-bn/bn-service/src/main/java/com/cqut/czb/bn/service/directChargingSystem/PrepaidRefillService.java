package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingCommodityDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.UserCardRelationDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PrepaidRefillService {
    List<DirectChargingCommodity> getGoodsPrice(Integer type);

    PageInfo<DirectChargingCommodity> getAllCommodity(DirectChargingCommodity directChargingCommodity, PageDTO pageDTO);

    Boolean addCommodity(DirectChargingCommodity directChargingCommodity);

    Boolean updateCommodity(DirectChargingCommodityDto directChargingCommodityDto);

    UserCardRelationDto getInfoNum(String userId);
}
