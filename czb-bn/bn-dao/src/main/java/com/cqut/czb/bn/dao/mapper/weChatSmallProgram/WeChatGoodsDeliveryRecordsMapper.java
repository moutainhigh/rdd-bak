package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatGoodsDeliveryRecords;

public interface WeChatGoodsDeliveryRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(WeChatGoodsDeliveryRecords record);

    int insertSelective(WeChatGoodsDeliveryRecords record);

    WeChatGoodsDeliveryRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(WeChatGoodsDeliveryRecords record);

    int updateByPrimaryKey(WeChatGoodsDeliveryRecords record);
}