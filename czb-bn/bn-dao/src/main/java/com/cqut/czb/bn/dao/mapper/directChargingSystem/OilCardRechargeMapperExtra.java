package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.dto.directChargingSystem.TotalConsumptionDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.UserCardRelation;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OilCardRechargeMapperExtra {
    /**
     * 话费订单
     * @param userId
     * @param type
     * @return
     */
    List<DirectChargingOrderDto> getOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);

    /**
     * 油卡充值订单
     * @param userId
     * @param type
     * @return
     */
    List<DirectChargingOrderDto> getOilOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);

    /**
     * 单次话费订单
     * @param userId
     * @param type
     * @return
     */
    List<DirectChargingOrderDto> getOnceOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);

    /**
     * 单次油卡充值订单
     * @param userId
     * @param type
     * @return
     */
    List<DirectChargingOrderDto> getOnceSOilOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);

    List<DirectChargingOrderDto> getOnceCOilOrderInfoList(@Param("userId")String userId, @Param("type")Integer type);


    List<DirectChargingOrderDto> getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    List<DirectChargingOrderDto> getAllOnceOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    TotalConsumptionDto getTotalConsumption(@Param("type") int type);

    List<DirectChargingOrderDto> getAllUserCard(DirectChargingOrderDto directChargingOrderDto);

    String isNeedLogin();

    int isExistOilCard(OilCardBinging oilCardBinging);

    int isExistOilCardUser(@Param("userId")String userId);

    int upDatePetrolNum(@Param("userId")String userId, @Param("oilCardBinging")OilCardBinging oilCardBinging);

    int insertPetrolNum(UserCardRelation userCardRelation);

    int insertOrder(DirectChargingOrderDto directChargingOrderDto);

    int insertOilOrder(DirectChargingOrderDto directChargingOrderDto);

    int updateRechargeRecord(DirectChargingOrderDto directChargingOrderDto);

    int updateOrderState(DirectChargingOrderDto directChargingOrderDto);

    DirectChargingOrderDto getOrder(String orderId);

    String getAccount(String userId);

    Double getDirectRechargeAmount(@Param("commodityId")String commodityId);

    Double getMaxIntegralAmount(@Param("commodityId")String commodityId);
}
