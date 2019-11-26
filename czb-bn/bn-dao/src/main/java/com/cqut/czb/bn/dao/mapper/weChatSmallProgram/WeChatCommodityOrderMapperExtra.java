package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-26 16:35
 */
public interface WeChatCommodityOrderMapperExtra {
    WCPCommodityOrderDTO selectCurrentOrder(String userId);

    List<WCPCommodityOrderDTO> selectAllCommodityOrder(String userId);

    Integer updateCommodityDeliveryState(@Param("userId") String userId, @Param("orderId") String orderId);

    WCPCommodityOrderDTO selectOneCommodityOrderById(@Param("userId") String userId, @Param("orderId") String orderId);
}