package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeChatCommodityOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WeChatCommodityOrder record);

    int insertSelective(WeChatCommodityOrder record);

    WeChatCommodityOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WeChatCommodityOrder record);

    int updateByPrimaryKey(WeChatCommodityOrder record);

    /**
     * 分页与查询
     *
     * @param record
     * @return
     */
    List<WeChatCommodityOrderDTO> getTableList(WeChatCommodityOrderDTO record);

    /**
     * 作废订单
     *
     * @param orderId
     * @return
     */
    int obsoleteOrder(@Param("orderId") String orderId);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    WeChatCommodityOrderDetail getOrderDetail(@Param("orderId") String orderId);
}