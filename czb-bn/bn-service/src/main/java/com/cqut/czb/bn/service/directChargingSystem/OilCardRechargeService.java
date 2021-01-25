package com.cqut.czb.bn.service.directChargingSystem;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public interface OilCardRechargeService{
    List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type);

    JSONResult getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult bindingOilCard(String userId, OilCardBinging oilCardBinging);

    JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto);

    JSONResult updateOrderState(DirectChargingOrderDto directChargingOrderDto);

    /**
     * 用支付宝充值油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    String AlipayRechargeDirect(DirectChargingOrderDto directChargingOrderDto);

    String aliPayReturn(HttpServletRequest request,String consumptionType);

}
