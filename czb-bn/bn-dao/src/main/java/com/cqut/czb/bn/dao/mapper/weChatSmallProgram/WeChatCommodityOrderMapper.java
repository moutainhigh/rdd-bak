package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;

public interface WeChatCommodityOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WeChatCommodityOrder record);

    int insertSelective(WeChatCommodityOrder record);

    WeChatCommodityOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WeChatCommodityOrder record);

    int updateByPrimaryKey(WeChatCommodityOrder record);
}