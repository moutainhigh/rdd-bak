package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.entity.directChargingSystem.UserCardRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OilCardRechargeMapperExtra {
    List<DirectChargingOrderDto> getOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);

    int isExistOilCard(OilCardBinging oilCardBinging);

    int isExistOilCardUser(@Param("userId")String userId);

    int upDatePetrolNum(@Param("userId")String userId, @Param("oilCardBinging")OilCardBinging oilCardBinging);

    int insertPetrolNum(UserCardRelation userCardRelation);
}
