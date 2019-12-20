package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WechatCommodityAttr;

public interface WechatCommodityAttrMapper {
    int deleteByPrimaryKey(String commodityAttrId);

    int insert(WechatCommodityAttr record);

    int insertSelective(WechatCommodityAttr record);

    WechatCommodityAttr selectByPrimaryKey(String commodityAttrId);

    int updateByPrimaryKeySelective(WechatCommodityAttr record);

    int updateByPrimaryKey(WechatCommodityAttr record);
}