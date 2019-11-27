package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-26 16:07
 */
public interface WCPCommodityOrderService {
    WCPCommodityOrderDTO getCurrentOrder(String userId);

    List<WCPCommodityOrderDTO> getAllCommodityOrder(String userId);

    Boolean comfirmDeliveryState(String userId, String orderId);

    WCPCommodityOrderDTO getOneCommodityOrderById(String userId, String orderId);
}