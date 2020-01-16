package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityComdirmOrderDTO;
import org.apache.ibatis.annotations.Param;

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

    Boolean comfirmCommodityOrder(String userId, WeChatCommodityComdirmOrderDTO weChatCommodityComdirmOrderDTO);

    WCPCommodityOrderDTO getOneCommodityOrderByShop(String userId, String orderId);

    List<WCPCommodityOrderDTO> getAllCommodityOrderByLeader(@Param("userId") String userId,@Param("orderState") Integer orderState,@Param("page") Integer page);

    Double getTotalPrice(@Param("userId") String userId,@Param("orderState") Integer orderState);
}