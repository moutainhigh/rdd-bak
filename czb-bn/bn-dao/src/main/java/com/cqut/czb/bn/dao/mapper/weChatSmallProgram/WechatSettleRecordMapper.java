package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WechatSettleRecord;

public interface WechatSettleRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(WechatSettleRecord record);

    int insertSelective(WechatSettleRecord record);

    WechatSettleRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(WechatSettleRecord record);

    int updateByPrimaryKey(WechatSettleRecord record);
}