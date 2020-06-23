package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;


public interface WeChatCommodityMapper {
    int deleteByPrimaryKey(String commodityId);

    int insert(WeChatCommodity record);

    int insertSelective(WeChatCommodity record);

    WeChatCommodity selectByPrimaryKey(String commodityId);

    int updateByPrimaryKeySelective(WeChatCommodity record);

    int updateByPrimaryKeyWithBLOBs(WeChatCommodity record);

    int updateByPrimaryKey(WeChatCommodity record);
}