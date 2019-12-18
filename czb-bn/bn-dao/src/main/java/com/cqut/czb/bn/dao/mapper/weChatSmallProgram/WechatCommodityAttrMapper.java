package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityAttr;

public interface WechatCommodityAttrMapper {
    int deleteByPrimaryKey(String commodityAttrId);

    int insert(WeChatCommodityAttr record);

    int insertSelective(WeChatCommodityAttr record);

    WeChatCommodityAttr selectByPrimaryKey(String commodityAttrId);

    int updateByPrimaryKeySelective(WeChatCommodityAttr record);

    int updateByPrimaryKey(WeChatCommodityAttr record);
}