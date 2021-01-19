package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingCommodityDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.UserCardRelationDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrepaidRefillMapperExtra {
    List<DirectChargingCommodity> getGoodsPrice(Integer type);

    List<DirectChargingCommodity> selectAllCommodity(DirectChargingCommodity directChargingCommodity);

    Integer addCommodity(DirectChargingCommodity directChargingCommodity);

    Integer updateCommodity(DirectChargingCommodityDto directChargingCommodityDto);

    UserCardRelationDto getInfoNum(String userId);
}
