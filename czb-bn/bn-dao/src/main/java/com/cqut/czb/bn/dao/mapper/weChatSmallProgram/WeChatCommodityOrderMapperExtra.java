package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityComdirmOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
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

    /**
     * 分页与查询（后台管理系统）
     *
     * @param record
     * @return
     */
    List<WeChatCommodityOrderDTO> getTableList(WeChatCommodityOrderDTO record);

    /**
     * 作废订单（后台管理系统）
     *
     * @param orderId
     * @return
     */
    int obsoleteOrder(@Param("orderId") String orderId);

    /**
     * 获取订单详情（后台管理系统）
     *
     * @param orderId
     * @return
     */
    WeChatCommodityOrderDetail getOrderDetail(@Param("orderId") String orderId);

    /**
     * 获取订单处理相关信息详情（后台管理系统）
     *
     * @param orderId
     * @return
     */
    WeChatCommodityOrderProcess getOrderProcessInfo(@Param("orderId") String orderId);

    /**
     * 处理订单（核销）（后台管理系统）
     *
     * @param input
     * @return
     */
    int dealOrderEl(WeChatCommodityOrderProcess input);

    /**
     * 处理订单（寄送）（后台管理系统）
     * 更新
     * @param input
     * @return
     */
    int dealOrderSend(WeChatCommodityOrderProcess input);

    /**
     * 商家在微信小程序上确认订单
     * @param weChatCommodityComdirmOrderDTO
     * @return
     */
    Integer updateCommodityOrderState(WeChatCommodityComdirmOrderDTO weChatCommodityComdirmOrderDTO);
}